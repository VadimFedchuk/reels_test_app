package com.example.instagramtestapp.data.repository

import com.example.instagramtestapp.data.api.ClipsApi
import com.example.instagramtestapp.data.mapper.toDomain
import com.example.instagramtestapp.domain.model.ClipsFeed
import com.example.instagramtestapp.domain.repository.ClipsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClipsRepositoryImpl(
    private val api: ClipsApi
) : ClipsRepository {

    override suspend fun getFeeds(): Result<ClipsFeed> = withContext(Dispatchers.IO) {
        runCatching {
            api.getClips().toDomain()
        }
    }
}