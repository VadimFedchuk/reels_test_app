package com.example.instagramtestapp.domain.repository

import com.example.instagramtestapp.domain.model.ClipsFeed

interface ClipsRepository {
    suspend fun getFeeds(): Result<ClipsFeed>
}
