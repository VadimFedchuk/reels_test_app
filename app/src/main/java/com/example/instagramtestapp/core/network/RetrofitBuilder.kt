package com.example.instagramtestapp.core.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun provideMoshi(): Moshi =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

fun provideOkHttp(apiKey: String): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(StorytellerAuthInterceptor(apiKey))
        .build()

fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String, moshi: Moshi): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()