package com.vei.controller.client.dto

import com.vei.model.Client
import com.vei.model.ClientCategory
import com.vei.model.ClientContact
import com.vei.model.ClientPriority
import com.vei.utils.extensions.toValidObjectIdOrThrow
import com.vei.utils.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ClientDto(
    val id: String? = null,
    val name: String,
    val category: ClientCategory,
    val priority: ClientPriority,
    @Serializable(LocalDateSerializer::class)
    val cooperationStart: LocalDate,
    @Serializable(LocalDateSerializer::class)
    val cooperationEnd: LocalDate?,
    val description: String,
    val contact: ClientContactDto,
)

@Serializable
data class ClientContactDto(
    val legalName: String,
    val registrationNumber: String,
    val address: String,
    val note: String,
)

fun ClientDto.toModel() = Client(
    id = id?.toValidObjectIdOrThrow(),
    name = name,
    category = category,
    priority = priority,
    cooperationStart = cooperationStart,
    cooperationEnd = cooperationEnd,
    description = description,
    contact = contact.toModel(),
)

fun ClientContactDto.toModel() = ClientContact(
    legalName = legalName,
    registrationNumber = registrationNumber,
    address = address,
    note = note,
)

fun Client.toDto() = ClientDto(
    id = id?.toHexString(),
    name = name,
    category = category,
    priority = priority,
    cooperationStart = cooperationStart,
    cooperationEnd = cooperationEnd,
    description = description,
    contact = contact.toDto(),
)

fun ClientContact.toDto() = ClientContactDto(
    legalName = legalName,
    registrationNumber = registrationNumber,
    address = address,
    note = note,
)
