package com.vei.services

import com.vei.model.FillableSlot
import com.vei.model.Person
import com.vei.model.SlotOptionState
import com.vei.services.model.DashboardStatistics
import com.vei.services.model.PersonAllocation
import com.vei.services.model.PersonStatusInTime
import com.vei.services.model.PersonWithStatus
import com.vei.services.model.SpecificSlotOption
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId
import org.koin.core.annotation.Singleton
import java.time.LocalDate

@Singleton
class StatisticsService(
    private val slotService: FillableSlotService,
    private val peopleService: PeopleService,
    private val projectService: ProjectService,
    private val clientService: ClientService,
) {

    suspend fun getPeopleAllocationsForDateRange(from: LocalDate, to: LocalDate) = coroutineScope {
        val slots = async { slotService.findAllInRange(from, to).toList() }
        val allPeople = async { peopleService.findAll().toList().associateBy { it.id!! } }
        val allProjects = async { projectService.findAll().toList().associateBy { it.id!! } }
        val allClients = async { clientService.findAll().toList().associateBy { it.id!! } }

        slots.await().flatMap {
            val project = allProjects.await().getValue(it.belongsToProject)
            val client = allClients.await().getValue(project.belongsToClient)
            val people =
                it.poolOfPossibleFillables.map { slotOption -> allPeople.await().getValue(slotOption.personId) }

            val slotOptionsByPerson = it.poolOfPossibleFillables.associateBy { slotOption -> slotOption.personId }

            people.map { person ->
                PersonAllocation(
                    from = it.startDate,
                    to = it.endDate,
                    slot = it,
                    slotOption = slotOptionsByPerson.getValue(person.id!!),
                    person = person,
                    project = project,
                    client = client,
                )
            }
        }
    }

    suspend fun getPeopleStatusOnPointInTime(date: LocalDate) = coroutineScope {
        val allPeople = async { peopleService.findAll().toList() }
        val slots = async { slotService.findAll().toList() }

        val slotsGroupedByPerson = slots.await().groupSlotOptionsByPersonId()

        allPeople.await().map { person ->
            val allocationToSlots = slotsGroupedByPerson.getAllSlotsWherePersonIsIncluded(person)

            val currentAndFutureSlots = allocationToSlots.filter { it.slot.endDate > date }
            val slotsSortedByStartDate = currentAndFutureSlots.sortedBy { it.slot.startDate }
            val slotActiveOnDateOrNull = slotsSortedByStartDate.firstWhereDateIsInSlotDateRangeOrNull(date)

            val state = slotActiveOnDateOrNull.getPersonStatusInTime()
            PersonWithStatus(
                status = state,
                specificSlotOption = slotActiveOnDateOrNull,
                person = person,
            )
        }
    }

    suspend fun getDashboardStatistics() = coroutineScope {
        val numberOfClients = async { clientService.countAll() }
        val numberOfProjects = async { projectService.countAll() }
        val numberOfPeople = async { peopleService.countAll() }

        DashboardStatistics(
            numberOfClients = numberOfClients.await().toInt(),
            numberOfProjects = numberOfProjects.await().toInt(),
            numberOfPeople = numberOfPeople.await().toInt(),
        )
    }
}

private fun List<FillableSlot>.groupSlotOptionsByPersonId() = flatMap {
    it.poolOfPossibleFillables.map { slotOption ->
        SpecificSlotOption(slot = it, slotOption = slotOption)
    }
}.groupBy { it.slotOption.personId }

private fun Map<ObjectId, List<SpecificSlotOption>>.getAllSlotsWherePersonIsIncluded(person: Person) =
    this[person.id!!] ?: emptyList()

private fun List<SpecificSlotOption>.firstWhereDateIsInSlotDateRangeOrNull(date: LocalDate) = firstOrNull {
    val slotRange = it.slot.startDate..it.slot.endDate
    slotRange.contains(date)
}

private fun SpecificSlotOption?.getPersonStatusInTime() = this?.let { foundSlot ->
    when (foundSlot.slotOption.state) {
        SlotOptionState.PREBOOKED -> PersonStatusInTime.PREBOOKED
        SlotOptionState.HARDBOOKED -> PersonStatusInTime.HARDBOOKED_ON_SLOT
    }
} ?: PersonStatusInTime.BENCH
