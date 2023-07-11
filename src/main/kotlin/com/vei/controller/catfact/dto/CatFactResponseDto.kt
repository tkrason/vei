package com.vei.controller.catfact.dto

import com.vei.model.CatFact
import kotlinx.serialization.Serializable

@Serializable
data class CatFactResponseDto(
    val id: String?,
    val fact: String,
)

fun CatFact.toDto() = CatFactResponseDto(id = id?.toHexString(), fact = fact)
