package com.vei.services

import com.vei.model.FillableSlot
import com.vei.repository.FillableSlotRepository
import kotlinx.coroutines.coroutineScope
import org.bson.types.ObjectId
import org.koin.core.annotation.Singleton

@Singleton
class FillableSlotService(
    private val fillableSlotRepository: FillableSlotRepository,
    private val peopleService: PeopleService,
) : ModelService<FillableSlot>(fillableSlotRepository) {

    suspend fun getAllSlotsOnProject(projectId: ObjectId) = fillableSlotRepository.getAllSlotsOnProject(projectId)

    suspend fun getAllPeopleInTheSlot(slotId: ObjectId) = coroutineScope {
        val slot = findModelByIdOrNull(slotId.toHexString()) ?: error("Slot does not exist!")
        peopleService.findManyByIds(slot.poolOfPossibleFillables)
    }

    suspend fun addPersonInTheSlot(slotId: ObjectId, personId: ObjectId) {
        val person = peopleService.findModelByIdOrNull(personId.toHexString()) ?: error("Person does not exist!")

        // From Mongo id is guaranteed to be set
        fillableSlotRepository.addPersonInTheSlot(slotId, person.id!!)
    }

    suspend fun deletePersonFromSlot(slotId: ObjectId, personId: ObjectId) =
        fillableSlotRepository.deletePersonFromSlot(slotId, personId)
}
