package com.vei.services

import com.vei.model.FillableSlot
import com.vei.repository.FillableSlotRepository
import org.bson.types.ObjectId
import org.koin.core.annotation.Singleton

@Singleton
class FillableSlotService(
    private val fillableSlotRepository: FillableSlotRepository,
) : ModelService<FillableSlot>(fillableSlotRepository) {

    suspend fun getAllSlotsOnProject(projectId: ObjectId) = fillableSlotRepository.getAllSlotsOnProject(projectId)
}
