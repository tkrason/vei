package com.vei.controller.fillableslot

import com.vei.controller.Controller
import com.vei.controller.RestController
import com.vei.controller.common.dto.ListWrapperDto
import com.vei.controller.fillableslot.dto.FillableSlotDto
import com.vei.controller.fillableslot.dto.SlotOptionDto
import com.vei.controller.fillableslot.dto.toDto
import com.vei.controller.fillableslot.dto.toModel
import com.vei.controller.person.dto.PersonDto
import com.vei.controller.person.dto.toDto
import com.vei.model.FillableSlot
import com.vei.services.FillableSlotService
import com.vei.utils.extensions.toValidObjectIdOrThrow
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.util.getOrFail
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.flow.toList
import org.koin.core.annotation.Singleton

@Singleton(binds = [Controller::class])
class FillableSlotController(
    private val fillableSlotService: FillableSlotService,
) : RestController<FillableSlot, FillableSlotDto, FillableSlotDto>(
    basePath = "api/v1",
    autoRegisterRoutes = true,
    service = fillableSlotService,
) {
    override fun Route.additionalRoutesForRegistration() {
        getPeopleInTheSlot()
        addPersonIntoSlot()
        deletePersonFromSlot()
    }

    override fun getNameOfModelForRestPath() = "fillable-slot"

    override fun requestDtoTypeInfo() = typeInfo<FillableSlotDto>()
    override fun listRequestTypeInfo() = typeInfo<ListWrapperDto<FillableSlotDto>>()

    override fun responseDtoTypeInfo() = requestDtoTypeInfo()
    override fun listResponseDtoTypeInfo() = listRequestTypeInfo()

    override fun FillableSlot.toResponseDto() = toDto()
    override fun FillableSlotDto.requestToModel() = toModel()

    private fun Route.getPeopleInTheSlot() = get("${getNameOfModelForRestPath()}/{id}/people", {
        tags = openApiTags
        request { pathParameter<String>("id") { this.description = "MongoDB ObjectId of Fillable slot" } }
        response {
            HttpStatusCode.NotFound to { }
            HttpStatusCode.OK to { body<ListWrapperDto<PersonDto>>() }
        }
    }) {
        val id = call.parameters.getOrFail("id")
        val peopleInTheSlot = fillableSlotService
            .getAllPeopleInTheSlot(id.toValidObjectIdOrThrow())
            .toList()
            .map { it.toDto() }

        call.respond(ListWrapperDto(peopleInTheSlot))
    }

    private fun Route.addPersonIntoSlot() = post("${getNameOfModelForRestPath()}/{id}/people", {
        tags = openApiTags
        request {
            pathParameter<String>("id") { this.description = "MongoDB ObjectId of Fillable slot" }
            body<SlotOptionDto>()
        }
        response {
            HttpStatusCode.NotFound to { }
            HttpStatusCode.OK to { }
        }
    }) {
        val slotId = call.parameters.getOrFail("id")
        val body = call.receive<SlotOptionDto>()

        fillableSlotService.addPersonInTheSlot(slotId.toValidObjectIdOrThrow(), body.toModel())
        call.respond(HttpStatusCode.OK)
    }

    private fun Route.deletePersonFromSlot() = delete("${getNameOfModelForRestPath()}/{id}/people/{personId}", {
        tags = openApiTags
        request { pathParameter<String>("personId") { this.description = "MongoDB ObjectId of Person to delete" } }
        response {
            HttpStatusCode.NotFound to { }
            HttpStatusCode.OK to { }
        }
    }) {
        val slotId = call.parameters.getOrFail("id")
        val personId = call.parameters.getOrFail("personId")

        fillableSlotService.deletePersonFromSlot(slotId.toValidObjectIdOrThrow(), personId.toValidObjectIdOrThrow())
        call.respond(HttpStatusCode.OK)
    }
}
