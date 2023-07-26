package com.vei.controller.stats

import com.vei.controller.Controller
import com.vei.controller.stats.dto.PeopleOnProjectsDto
import com.vei.controller.stats.dto.PersonStatusOnPointInTimeDto
import com.vei.controller.stats.dto.toDto
import com.vei.controller.stats.dto.toPeopleOnProjectsDto
import com.vei.services.StatisticsService
import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.util.getOrFail
import org.koin.core.annotation.Singleton
import java.time.LocalDate

@Singleton(binds = [Controller::class])
class StatisticsController(
    private val statisticsService: StatisticsService,
) : Controller(
    basePath = "api/v1/stats",
    useBearerAuth = true,
) {
    override fun Route.routesForRegistrationOnBasePath() {
        getPeopleOnSlotsStatsInRange()
        getPeopleStatusOnPointInTime()
        getDashboardStatistics()
    }

    val openApiTags = listOf("stats")

    private fun Route.getPeopleOnSlotsStatsInRange() = get("/people-allocations", {
        tags = openApiTags
        request {
            queryParameter<LocalDate>("start-date") { this.description = "Start analysis from date" }
            queryParameter<LocalDate>("end-date") { this.description = "End analysis at date" }
        }
        response { HttpStatusCode.OK to { body<List<PeopleOnProjectsDto>>() } }
    }) {
        val start = call.parameters.getOrFail("start-date")
        val end = call.parameters.getOrFail("end-date")

        val allocations = statisticsService.getPeopleAllocationsForDateRange(LocalDate.parse(start), LocalDate.parse(end))
        call.respond(allocations.map { it.toPeopleOnProjectsDto() })
    }

    private fun Route.getPeopleStatusOnPointInTime() = get("/people-state", {
        tags = openApiTags
        request {
            queryParameter<LocalDate>("date") { this.description = "Date of analysis" }
        }
        response { HttpStatusCode.OK to { body<List<PersonStatusOnPointInTimeDto>>() } }
    }) {
        val date = call.parameters.getOrFail("date")

        val peopleStatesOnDate = statisticsService.getPeopleStatusOnPointInTime(LocalDate.parse(date))
        call.respond(peopleStatesOnDate.map { it.toDto() })
    }

    private fun Route.getDashboardStatistics() = get("/dashboard", {
        tags = openApiTags
    }) {
        call.respond(statisticsService.getDashboardStatistics().toDto())
    }
}
