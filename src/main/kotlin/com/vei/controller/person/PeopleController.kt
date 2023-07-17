package com.vei.controller.person

import com.vei.controller.Controller
import com.vei.controller.RestController
import com.vei.controller.common.dto.ListWrapperDto
import com.vei.controller.person.dto.PersonDto
import com.vei.controller.person.dto.toDto
import com.vei.controller.person.dto.toModel
import com.vei.model.Person
import com.vei.services.PeopleService
import io.ktor.server.routing.Route
import io.ktor.util.reflect.typeInfo
import org.koin.core.annotation.Singleton

@Singleton(binds = [Controller::class])
class PeopleController(
    peopleService: PeopleService,
) : RestController<Person, PersonDto, PersonDto>(
    basePath = "api/v1",
    autoRegisterRoutes = true,
    service = peopleService,
) {
    override fun Route.additionalRoutesForRegistration() {}
    override fun getNameOfModelForRestPath(): String = "people"

    override fun requestDtoTypeInfo() = typeInfo<PersonDto>()
    override fun listRequestTypeInfo() = typeInfo<ListWrapperDto<PersonDto>>()
    override fun responseDtoTypeInfo() = requestDtoTypeInfo()
    override fun listResponseDtoTypeInfo() = listRequestTypeInfo()

    override fun Person.toResponseDto() = toDto()
    override fun PersonDto.requestToModel() = toModel()
}
