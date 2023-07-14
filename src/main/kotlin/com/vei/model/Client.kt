package com.vei.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDate

data class Client(
    @BsonId override val id: ObjectId? = null,
    val name: String,
    val category: ClientCategory,
    val priority: ClientPriority,
    val cooperationStart: LocalDate,
    val cooperationEnd: LocalDate?,
    val description: String,
    val contact: ClientContact,
) : Model

data class ClientOption(
    @BsonId val id: ObjectId,
    val name: String,
)

data class ClientDeleteResponse(
    val deletedClients: Long,
    val deletedProjects: Long,
)

enum class ClientCategory {
    FINTECH,
    MEDTECH,
    CRYPTO,
}

enum class ClientPriority {
    HIGH,
    MEDIUM,
    LOW,
}

data class ClientContact(
    val legalName: String,
    val registrationNumber: String,
    val address: String,
    val note: String,
)
