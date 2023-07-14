package com.vei.controller.client.dto

import com.vei.model.ClientDeleteResponse

data class ClientDeleteResponseDto(
    val deletedClients: Long,
    val deletedProjects: Long,
)

fun ClientDeleteResponse.toDto() = ClientDeleteResponseDto(
    deletedClients = deletedClients,
    deletedProjects = deletedProjects,
)
