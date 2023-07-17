package com.vei.controller.project

import com.vei.controller.Controller
import com.vei.controller.RestController
import com.vei.controller.common.dto.ListWrapperDto
import com.vei.controller.fillableslot.dto.FillableSlotDto
import com.vei.controller.fillableslot.dto.toDto
import com.vei.controller.project.dto.ProjectDto
import com.vei.controller.project.dto.toDto
import com.vei.controller.project.dto.toModel
import com.vei.model.Project
import com.vei.services.FillableSlotService
import com.vei.services.ProjectService
import com.vei.utils.extensions.toValidObjectIdOrThrow
import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.util.getOrFail
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.flow.toList
import org.koin.core.annotation.Singleton

@Singleton(binds = [Controller::class])
class ProjectController(
    projectService: ProjectService,
    private val fillableSlotService: FillableSlotService,
) : RestController<Project, ProjectDto, ProjectDto>(
    basePath = "api/v1",
    autoRegisterRoutes = true,
    service = projectService,
) {
    override fun Route.additionalRoutesForRegistration() {
        getAllSlotsOnProject()
    }

    override fun getNameOfModelForRestPath(): String = "project"

    override fun ProjectDto.requestToModel(): Project = toModel()
    override fun Project.toResponseDto(): ProjectDto = toDto()

    override fun requestDtoTypeInfo(): TypeInfo = typeInfo<ProjectDto>()
    override fun listRequestTypeInfo(): TypeInfo = typeInfo<ListWrapperDto<ProjectDto>>()
    override fun responseDtoTypeInfo(): TypeInfo = requestDtoTypeInfo()
    override fun listResponseDtoTypeInfo(): TypeInfo = listRequestTypeInfo()

    private fun Route.getAllSlotsOnProject() = get("/${getNameOfModelForRestPath()}/{id}/fillable-slot", {
        tags = openApiTags
        request { pathParameter<String>("id") { this.description = "MongoDB ObjectId of Project" } }
        response {
            HttpStatusCode.OK to { body<ListWrapperDto<FillableSlotDto>>() }
        }
    }) {
        val id = call.parameters.getOrFail("id")
        val slots = fillableSlotService
            .getAllSlotsOnProject(id.toValidObjectIdOrThrow())
            .toList()
            .map { it.toDto() }

        call.respond(ListWrapperDto(data = slots))
    }
}
