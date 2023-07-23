package com.vei.controller.stats

import com.vei.controller.Controller
import com.vei.controller.stats.dto.toPeopleOnProjectsDto
import com.vei.services.StatisticsService
import io.github.smiley4.ktorswaggerui.dsl.get
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
    }

    val openApiTags = listOf("stats")

    private fun Route.getPeopleOnSlotsStatsInRange() = get("/people-allocations", {
        tags = openApiTags
        request {
            queryParameter<LocalDate>("start-date") { this.description = "Start analysis from date" }
            queryParameter<LocalDate>("end-date") { this.description = "End analysis at date" }
        }
    }) {
        val start = call.parameters.getOrFail("start-date")
        val end = call.parameters.getOrFail("end-date")

        val allocations = statisticsService.getPeopleAllocationsForDateRange(LocalDate.parse(start), LocalDate.parse(end))
        call.respond(allocations.map { it.toPeopleOnProjectsDto() })
    }
}
