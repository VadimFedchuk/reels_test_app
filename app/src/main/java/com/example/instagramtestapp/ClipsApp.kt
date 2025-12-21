package com.example.instagramtestapp

import android.app.Application
import com.example.instagramtestapp.di.modules.clipsDataModule
import com.example.instagramtestapp.di.modules.connectivityModule
import com.example.instagramtestapp.di.modules.mainFeatureModule
import com.example.instagramtestapp.di.modules.networkModule
import com.example.instagramtestapp.di.modules.playerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ClipsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ClipsApp)
            modules(
                networkModule,
                clipsDataModule,
                mainFeatureModule,
                playerModule,
                connectivityModule
            )
        }
    }
}