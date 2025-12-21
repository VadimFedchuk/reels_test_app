package com.example.instagramtestapp.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedResponseDto(
    @Json(name = "feedTitle") val feedTitle: String?,
    @Json(name = "clips") val clips: List<ClipDto> = emptyList()
)
@JsonClass(generateAdapter = true)
data class ClipDto(
    @Json(name = "id") val id: String,
    @Json(name = "description") val description: String?,
    @Json(name = "url") val url: String,
    @Json(name = "likeCountDisplay") val likeCountDisplay: String?,
    @Json(name = "shareCountDisplay") val shareCountDisplay: String?,
)