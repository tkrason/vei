package com.vei.controller.client

import com.vei.controller.Controller
import com.vei.controller.RestController
import com.vei.controller.client.dto.ClientDto
import com.vei.controller.client.dto.toDto
import com.vei.controller.client.dto.toModel
import com.vei.controller.common.dto.ListWrapperDto
import com.vei.model.Client
import com.vei.services.ClientService
import io.ktor.server.routing.Route
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import org.koin.core.annotation.Singleton

@Singleton(binds = [Controller::class])
class ClientController(
    clientService: ClientService,
) : RestController<Client, ClientDto, ClientDto>(
    basePath = "api/v1",
    autoRegisterRoutes = true,
    service = clientService,
) {
    override fun Route.additionalRoutesForRegistration() {}
    override fun getNameOfModelForRestPath(): String = "client"

    override fun requestDtoTypeInfo(): TypeInfo = typeInfo<ClientDto>()
    override fun listRequestTypeInfo(): TypeInfo = typeInfo<ListWrapperDto<ClientDto>>()
    override fun responseDtoTypeInfo(): TypeInfo = requestDtoTypeInfo()
    override fun listResponseDtoTypeInfo(): TypeInfo = listRequestTypeInfo()

    override fun Client.toResponseDto(): ClientDto = toDto()
    override fun ClientDto.requestToModel(): Client = toModel()
}
