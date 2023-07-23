package com.vei.services

import com.vei.services.model.PersonAllocation
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
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
}
