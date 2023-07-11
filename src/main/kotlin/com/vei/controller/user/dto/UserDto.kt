package com.vei.controller.user.dto

import com.vei.model.User
import com.vei.utils.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.time.LocalDate

@Serializable
data class UserDto(
    val id: String? = null,
    val username: String,
    @Serializable(LocalDateSerializer::class)
    val dateOfBirth: LocalDate,
    val articles: List<ArticleDto>,
)

fun User.toDto() = UserDto(
    id = id?.toHexString(),
    username = username,
    dateOfBirth = dateOfBirth,
    articles = articles.map { it.toDto() },
)

fun UserDto.toModel() = User(
    id = id?.let { ObjectId(it) },
    username = username,
    dateOfBirth = dateOfBirth,
    articles = articles.map { it.toModel() },
)
