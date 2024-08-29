package com.sharathkumar.ktor2

import kotlinx.serialization.Serializable


@Serializable
data class Respone(
    val hits:List<ImageResult>
)

@Serializable
data class ImageResult(
    val id: Int,
    val tags: String,
    val webformatURL: String
)