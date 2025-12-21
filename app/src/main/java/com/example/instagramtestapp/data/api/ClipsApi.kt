package com.example.instagramtestapp.data.api

import com.example.instagramtestapp.data.dto.FeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ClipsApi {
    @GET("api/app/clips/clipssample/clips")
    suspend fun getClips(
        @Query("ClientPlatform") clientPlatform: String = "Android",
        @Query("ClientVersion") clientVersion: String = "9.2.0",
    ): FeedResponseDto
}