package com.vei.controller.client

import com.vei.controller.Controller
import com.vei.controller.RestController
import com.vei.controller.client.dto.ClientDto
import com.vei.controller.client.dto.toDto
import com.vei.controller.client.dto.toModel
import com.vei.controller.common.dto.CountResponseDto
import com.vei.controller.common.dto.ListWrapperDto
import com.vei.controller.project.dto.ProjectDto
import com.vei.controller.project.dto.toDto
import com.vei.model.Client
import com.vei.services.ClientService
import com.vei.services.ProjectService
import com.vei.utils.extensions.toValidObjectIdOrThrow
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.util.getOrFail
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.flow.toList
import org.koin.core.annotation.Singleton

@Singleton(binds = [Controller::class])
class ClientController(
    private val clientService: ClientService,
    private val projectService: ProjectService,
) : RestController<Client, ClientDto, ClientDto>(
    basePath = "api/v1",
    autoRegisterRoutes = false,
    service = clientService,
) {
    override fun Route.additionalRoutesForRegistration() {
        countAll()

        findAll()
        findFirstByIdOrNull()

        saveOne()
        saveMany()

        updateOne()
        deleteClient() // not using the original delete endpoint

        getAllProjectsForClient()
        getAllClientsOptions()
    }

    override fun getNameOfModelForRestPath(): String = "client"

    override fun requestDtoTypeInfo(): TypeInfo = typeInfo<ClientDto>()
    override fun listRequestTypeInfo(): TypeInfo = typeInfo<ListWrapperDto<ClientDto>>()
    override fun responseDtoTypeInfo(): TypeInfo = requestDtoTypeInfo()
    override fun listResponseDtoTypeInfo(): TypeInfo = listRequestTypeInfo()

    override fun Client.toResponseDto(): ClientDto = toDto()
    override fun ClientDto.requestToModel(): Client = toModel()

    private fun Route.getAllProjectsForClient() = get("/${getNameOfModelForRestPath()}/{id}/projects", {
        request { pathParameter<String>("id") { this.description = "MongoDB ObjectId of Project" } }
        response {
            HttpStatusCode.OK to { body<ListWrapperDto<ProjectDto>>() }
        }
    }) {
        val id = call.parameters.getOrFail("id")
        val projects = projectService
            .getAllProjectsForClient(id.toValidObjectIdOrThrow())
            .toList()
            .map { it.toDto() }

        call.respond(ListWrapperDto(data = projects))
    }

    private fun Route.getAllClientsOptions() = get("/${getNameOfModelForRestPath()}/options") {
        call.respond(clientService.getAllClientsOptions().toList().map { it.toDto() })
    }

    fun Route.deleteClient() = delete("/${getNameOfModelForRestPath()}", {
        request { queryParameter<String>("id") }
        response { HttpStatusCode.OK to { body<CountResponseDto>() } }
    }) {
        val id = call.parameters.getOrFail("id")
        val count = clientService.deleteClient(id.toValidObjectIdOrThrow())
        call.respond(count.toDto())
    }
}
