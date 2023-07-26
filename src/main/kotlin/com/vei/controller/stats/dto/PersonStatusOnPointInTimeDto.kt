package com.vei.controller.stats.dto

import com.vei.services.model.PersonStatusInTime
import com.vei.services.model.PersonWithStatus
import com.vei.utils.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PersonStatusOnPointInTimeDto(
    val personId: String,
    val state: PersonStatusInTime,

    val slotId: String?,
    val projectId: String?,
    @Serializable(LocalDateSerializer::class)
    val from: LocalDate?,
    @Serializable(LocalDateSerializer::class)
    val to: LocalDate?,
)

fun PersonWithStatus.toDto() = PersonStatusOnPointInTimeDto(
    personId = person.id!!.toHexString(),
    state = status,
    slotId = specificSlotOption?.slot?.id?.toHexString(),
    projectId = specificSlotOption?.slot?.belongsToProject?.toHexString(),
    from = specificSlotOption?.slot?.startDate,
    to = specificSlotOption?.slot?.endDate,
)
