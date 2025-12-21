package com.example.instagramtestapp.di.modules

import com.example.instagramtestapp.data.repository.ClipsRepositoryImpl
import com.example.instagramtestapp.domain.repository.ClipsRepository
import org.koin.dsl.module

val clipsDataModule = module {
    single<ClipsRepository> { ClipsRepositoryImpl(api = get()) }
}