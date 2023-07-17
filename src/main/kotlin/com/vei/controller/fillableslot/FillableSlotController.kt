package com.vei.controller.fillableslot

import com.vei.controller.Controller
import com.vei.controller.RestController
import com.vei.controller.common.dto.ListWrapperDto
import com.vei.controller.fillableslot.dto.FillableSlotDto
import com.vei.controller.fillableslot.dto.toDto
import com.vei.controller.fillableslot.dto.toModel
import com.vei.model.FillableSlot
import com.vei.services.FillableSlotService
import io.ktor.server.routing.Route
import io.ktor.util.reflect.typeInfo
import org.koin.core.annotation.Singleton

@Singleton(binds = [Controller::class])
class FillableSlotController(
    fillableSlotService: FillableSlotService,
) : RestController<FillableSlot, FillableSlotDto, FillableSlotDto>(
    basePath = "api/v1",
    autoRegisterRoutes = true,
    service = fillableSlotService,
) {
    override fun Route.additionalRoutesForRegistration() {}

    override fun getNameOfModelForRestPath() = "fillable-slot"

    override fun requestDtoTypeInfo() = typeInfo<FillableSlotDto>()
    override fun listRequestTypeInfo() = typeInfo<ListWrapperDto<FillableSlotDto>>()

    override fun responseDtoTypeInfo() = requestDtoTypeInfo()
    override fun listResponseDtoTypeInfo() = listRequestTypeInfo()

    override fun FillableSlot.toResponseDto() = toDto()
    override fun FillableSlotDto.requestToModel() = toModel()
}
