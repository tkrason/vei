package com.vei.controller.fillableslot.dto

import com.vei.model.FillableSlot
import com.vei.model.SlotOption
import com.vei.model.SlotOptionState
import com.vei.utils.extensions.toValidObjectIdOrThrow
import com.vei.utils.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class FillableSlotDto(
    val id: String? = null,
    val product: String,
    @Serializable(LocalDateSerializer::class)
    val startDate: LocalDate,
    @Serializable(LocalDateSerializer::class)
    val endDate: LocalDate,
    val dailyRate: Int,
    val requiredNumberOfFillables: Int,

    val belongsToProject: String,
    val poolOfPossibleFillables: List<SlotOptionDto>,
)

@Serializable
data class SlotOptionDto(
    val id: String? = null,
    val personId: String,
    val state: SlotOptionState,
    val fte: Int,
)

fun FillableSlotDto.toModel() = FillableSlot(
    id = id?.toValidObjectIdOrThrow(),
    product = product,
    startDate = startDate,
    endDate = endDate,
    dailyRate = dailyRate,
    requiredNumberOfFillables = requiredNumberOfFillables,
    belongsToProject = belongsToProject.toValidObjectIdOrThrow(),
    poolOfPossibleFillables = poolOfPossibleFillables.map { it.toModel() },
)

fun FillableSlot.toDto() = FillableSlotDto(
    id = id?.toHexString(),
    product = product,
    startDate = startDate,
    endDate = endDate,
    dailyRate = dailyRate,
    requiredNumberOfFillables = requiredNumberOfFillables,
    belongsToProject = belongsToProject.toHexString(),
    poolOfPossibleFillables = poolOfPossibleFillables.map { it.toDto() },
)

fun SlotOption.toDto() = SlotOptionDto(
    personId = personId.toHexString(),
    state = state,
    fte = fte,
)

fun SlotOptionDto.toModel() = SlotOption(
    personId = personId.toValidObjectIdOrThrow(),
    state = state,
    fte = fte,
)
