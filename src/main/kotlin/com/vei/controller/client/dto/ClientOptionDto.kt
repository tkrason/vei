package com.vei.controller.client.dto

import com.vei.model.ClientOption
import kotlinx.serialization.Serializable

@Serializable
data class ClientOptionDto(
    val id: String,
    val name: String,
)

fun ClientOption.toDto() = ClientOptionDto(id = id.toHexString(), name = name)
