package com.vei.controller.user.dto

import com.vei.model.Article
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val articleName: String,
    val articleText: String,
)

fun Article.toDto() = ArticleDto(
    articleName = articleName,
    articleText = articleText,
)

fun ArticleDto.toModel() = Article(
    articleName = articleName,
    articleText = articleText,
)
