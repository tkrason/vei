package com.vei.controller.user

import com.vei.controller.Controller
import com.vei.controller.RestController
import com.vei.controller.common.dto.ListWrapperDto
import com.vei.controller.user.dto.UserDto
import com.vei.controller.user.dto.toDto
import com.vei.controller.user.dto.toModel
import com.vei.model.User
import com.vei.services.UserService
import io.ktor.server.routing.Route
import io.ktor.util.reflect.typeInfo
import org.koin.core.annotation.Singleton

@Singleton(binds = [Controller::class])
class UserController(
    userService: UserService,
) : RestController<User, UserDto, UserDto>(
    basePath = "api/v2",
    autoRegisterRoutes = true,
    service = userService,
) {
    override fun Route.additionalRoutesForRegistration() {}

    override fun getNameOfModelForRestPath() = "user"
    override fun User.toResponseDto(): UserDto = toDto()

    override fun UserDto.requestToModel() = toModel()

    override fun requestDtoTypeInfo() = typeInfo<UserDto>()

    override fun listRequestTypeInfo() = typeInfo<ListWrapperDto<UserDto>>()

    override fun responseDtoTypeInfo() = requestDtoTypeInfo()

    override fun listResponseDtoTypeInfo() = listRequestTypeInfo()
}
