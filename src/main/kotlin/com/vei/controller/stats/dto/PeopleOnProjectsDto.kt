package com.vei.controller.stats.dto

import com.vei.model.SlotOptionState
import com.vei.services.model.PersonAllocation
import com.vei.utils.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PeopleOnProjectsDto(
    val personId: String,
    val slotId: String,
    val projectId: String,
    val clientId: String,

    @Serializable(LocalDateSerializer::class)
    val start: LocalDate,
    @Serializable(LocalDateSerializer::class)
    val end: LocalDate,

    val state: SlotOptionState,
    val label: String,
)

fun PersonAllocation.toPeopleOnProjectsDto() = PeopleOnProjectsDto(
    personId = person.id!!.toHexString(),
    slotId = slot.id!!.toHexString(),
    projectId = project.id!!.toHexString(),
    clientId = client.id!!.toHexString(),

    start = from,
    end = to,
    state = slotOption.state,
    label = "${client.name} - ${project.name} - ${slot.product}  Rate: ${slot.dailyRate}, FTE: ${slotOption.fte}%",
)
