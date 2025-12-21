package com.example.instagramtestapp.di.modules

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

@OptIn(UnstableApi::class)
val playerModule = module {

    single<Cache> {
        val context = androidContext()
        val cacheDir = File(context.cacheDir, "media_cache")
        val evictor = LeastRecentlyUsedCacheEvictor(200L * 1024L * 1024L)
        val dbProvider = StandaloneDatabaseProvider(context)
        SimpleCache(cacheDir, evictor, dbProvider)
    }

    single<DataSource.Factory> {
        val upstream = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)

        CacheDataSource.Factory()
            .setCache(get())
            .setUpstreamDataSourceFactory(upstream)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    factory<ExoPlayer> {
        val context = androidContext()
        val dsFactory: DataSource.Factory = get()

        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(dsFactory))
            .build()
    }
}