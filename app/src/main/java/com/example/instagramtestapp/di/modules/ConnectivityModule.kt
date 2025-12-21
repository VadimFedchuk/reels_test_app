package com.example.instagramtestapp.di.modules

import com.example.instagramtestapp.core.connect.ConnectivityObserver
import com.example.instagramtestapp.core.connect.ConnectivityObserverImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val connectivityModule = module {
    single<ConnectivityObserver> { ConnectivityObserverImpl(androidContext()) }
}