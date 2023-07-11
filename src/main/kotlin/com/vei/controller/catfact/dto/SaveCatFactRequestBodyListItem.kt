package com.vei.controller.catfact.dto

import com.vei.model.CatFact
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class SaveCatFactRequestBodyListItem(
    val id: String? = null,
    val fact: String,
)

fun SaveCatFactRequestBodyListItem.toModel() = CatFact(id = id?.let { ObjectId(id) }, fact = fact)
