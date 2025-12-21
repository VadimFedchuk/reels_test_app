package com.example.instagramtestapp.di.modules

import com.example.instagramtestapp.presentation.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainFeatureModule = module {
    viewModel { MainViewModel(get(), get()) }
}