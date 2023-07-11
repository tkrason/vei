package com.vei.controller.project.dto

import com.vei.model.Project
import com.vei.model.ProjectCurrencies
import com.vei.model.ProjectPriority
import com.vei.model.ProjectType
import com.vei.utils.extensions.toValidObjectIdOrThrow
import com.vei.utils.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ProjectDto(
    val id: String? = null,
    val name: String,
    val priority: ProjectPriority,
    val type: ProjectType,
    @Serializable(LocalDateSerializer::class)
    val startDate: LocalDate,
    @Serializable(LocalDateSerializer::class)
    val endDate: LocalDate?,
    val description: String,
    val tentative: Boolean, // isTentative will now show on OpenApi specs
    val probability: Int,
    val currency: ProjectCurrencies,

    val belongsToClient: String,
)

fun ProjectDto.toModel() = Project(
    id = id?.toValidObjectIdOrThrow(),
    name = name,
    priority = priority,
    type = type,
    startDate = startDate,
    endDate = endDate,
    description = description,
    isTentative = tentative,
    probability = probability,
    currency = currency,
    belongsToClient = belongsToClient.toValidObjectIdOrThrow(),
)

fun Project.toDto() = ProjectDto(
    id = id?.toHexString(),
    name = name,
    priority = priority,
    type = type,
    startDate = startDate,
    endDate = endDate,
    description = description,
    tentative = isTentative,
    probability = probability,
    currency = currency,
    belongsToClient = belongsToClient.toHexString(),
)
