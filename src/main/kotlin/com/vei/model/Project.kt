package com.vei.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDate

data class Project(
    @BsonId override val id: ObjectId?,
    val name: String,
    val priority: ProjectPriority,
    val type: ProjectType,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val description: String,
    val isTentative: Boolean,
    val probability: Int,
    val currency: ProjectCurrencies,

    val belongsToClient: ObjectId,
) : Model

enum class ProjectPriority {
    URGENT,
    HIGH,
    MEDIUM,
    LOW,
}

enum class ProjectType {
    TIME_AND_MATERIAL,
    FIXED_PRICE_FIXED_SCOPE,
    OTHER,
}

enum class ProjectCurrencies {
    EUR,
    USD,
    GBP,
}
