package com.vei.controller.stats.dto

import com.vei.services.model.DashboardStatistics
import kotlinx.serialization.Serializable

@Serializable
data class DashboardStatisticsDto(
    val numberOfClients: Int,
    val numberOfProjects: Int,
    val numberOfPeople: Int,
)

fun DashboardStatistics.toDto() = DashboardStatisticsDto(
    numberOfClients = numberOfClients,
    numberOfProjects = numberOfProjects,
    numberOfPeople = numberOfPeople,
)
