package com.example.instagramtestapp.core.network

import okhttp3.Interceptor
import okhttp3.Response

class StorytellerAuthInterceptor(
    private val apiKey: String
) : Interceptor {

    companion object {
        const val QUERY_API_KEY = "x-storyteller-api-key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val newUrl = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, apiKey)
            .build()

        val newRequest = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}