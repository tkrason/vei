package com.vei.controller.catfact

import com.vei.controller.Controller
import com.vei.controller.RestController
import com.vei.controller.catfact.dto.CatFactResponseDto
import com.vei.controller.catfact.dto.SaveCatFactRequestBodyListItem
import com.vei.controller.catfact.dto.toDto
import com.vei.controller.catfact.dto.toModel
import com.vei.controller.common.dto.ListWrapperDto
import com.vei.model.CatFact
import com.vei.services.CatFactService
import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.util.reflect.typeInfo
import org.koin.core.annotation.Singleton

@Singleton(binds = [Controller::class])
class CatFactController(
    private val catFactService: CatFactService,
) : RestController<CatFact, SaveCatFactRequestBodyListItem, CatFactResponseDto>(
    basePath = "api/v1",
    autoRegisterRoutes = true,
    service = catFactService,
) {
    override fun Route.additionalRoutesForRegistration() {
        getFactFromApi()
    }

    override fun getNameOfModelForRestPath() = "cat-fact"

    override fun requestDtoTypeInfo() = typeInfo<SaveCatFactRequestBodyListItem>()
    override fun listRequestTypeInfo() = typeInfo<ListWrapperDto<SaveCatFactRequestBodyListItem>>()

    override fun responseDtoTypeInfo() = typeInfo<CatFactResponseDto>()
    override fun listResponseDtoTypeInfo() = typeInfo<ListWrapperDto<CatFactResponseDto>>()

    override fun CatFact.toResponseDto() = toDto()
    override fun SaveCatFactRequestBodyListItem.requestToModel() = toModel()

    private fun Route.getFactFromApi() = get("/${getNameOfModelForRestPath()}/api", {
        response { HttpStatusCode.OK to { body(responseDtoTypeInfo().type) } }
    }) {
        call.respond(catFactService.getFactFromApi().toResponseDto())
    }
}
