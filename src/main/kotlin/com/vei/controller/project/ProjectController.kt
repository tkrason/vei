package com.vei.controller.project

import com.vei.controller.Controller
import com.vei.controller.RestController
import com.vei.controller.common.dto.ListWrapperDto
import com.vei.controller.project.dto.ProjectDto
import com.vei.controller.project.dto.toDto
import com.vei.controller.project.dto.toModel
import com.vei.model.Project
import com.vei.services.ProjectService
import io.ktor.server.routing.Route
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import org.koin.core.annotation.Singleton

@Singleton(binds = [Controller::class])
class ProjectController(
    private val projectService: ProjectService,
) : RestController<Project, ProjectDto, ProjectDto>(
    basePath = "api/v1",
    autoRegisterRoutes = true,
    service = projectService,
) {
    override fun Route.additionalRoutesForRegistration() {}

    override fun getNameOfModelForRestPath(): String = "project"

    override fun ProjectDto.requestToModel(): Project = toModel()
    override fun Project.toResponseDto(): ProjectDto = toDto()

    override fun requestDtoTypeInfo(): TypeInfo = typeInfo<ProjectDto>()
    override fun listRequestTypeInfo(): TypeInfo = typeInfo<ListWrapperDto<ProjectDto>>()
    override fun responseDtoTypeInfo(): TypeInfo = requestDtoTypeInfo()
    override fun listResponseDtoTypeInfo(): TypeInfo = listRequestTypeInfo()
}
