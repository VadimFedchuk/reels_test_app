package com.example.instagramtestapp.di.modules

import com.example.instagramtestapp.BuildConfig
import com.example.instagramtestapp.data.api.ClipsApi
import com.example.instagramtestapp.core.network.provideMoshi
import com.example.instagramtestapp.core.network.provideOkHttp
import com.example.instagramtestapp.core.network.provideRetrofit
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideMoshi() }

    single { provideOkHttp(apiKey = BuildConfig.STORYTELLER_API_KEY) }

    single {
        provideRetrofit(
            okHttpClient = get(),
            baseUrl = BuildConfig.STORYTELLER_BASE_URL,
            moshi = get()
        )
    }

    single<ClipsApi> { get<Retrofit>().create(ClipsApi::class.java) }
}