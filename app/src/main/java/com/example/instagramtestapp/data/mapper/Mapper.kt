package com.example.instagramtestapp.data.mapper

import com.example.instagramtestapp.data.dto.ClipDto
import com.example.instagramtestapp.data.dto.FeedResponseDto
import com.example.instagramtestapp.domain.model.Clip
import com.example.instagramtestapp.domain.model.ClipsFeed

fun FeedResponseDto.toDomain(): ClipsFeed =
    ClipsFeed(
        feedTitle = feedTitle.orEmpty(),
        clips = clips.map { it.toDomain() }
    )

fun ClipDto.toDomain(): Clip =
    Clip(
        id = id,
        videoUrl = url,
        title = description.orEmpty(),
        likes = likeCountDisplay.orEmpty(),
        shares = shareCountDisplay.orEmpty()
    )