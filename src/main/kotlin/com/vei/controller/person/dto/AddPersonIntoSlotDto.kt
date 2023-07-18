package com.vei.controller.person.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddPersonIntoSlotDto(
    val personId: String,
)
