package com.example.instagramtestapp.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.instagramtestapp.domain.model.Clip
import org.koin.compose.koinInject

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClipsPager(clips: List<Clip>) {
    if (clips.isEmpty()) return

    val player: ExoPlayer = koinInject()

    LaunchedEffect(player) {
        player.repeatMode = Player.REPEAT_MODE_ONE
        player.playWhenReady = true
        player.volume = 1f
    }

    DisposableEffect(player) { onDispose { player.release() } }

    val startPage = remember(clips.size) {
        val mid = Int.MAX_VALUE / 2
        mid - (mid % clips.size)
    }

    val pagerState = rememberPagerState(
        initialPage = startPage,
        pageCount = { Int.MAX_VALUE }
    )

    val activePage by remember {
        derivedStateOf {
            calcActivePage(
                currentPage = pagerState.currentPage,
                offset = pagerState.currentPageOffsetFraction,
            )
        }
    }

    var lastClipId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(activePage, clips) {
        val clip = clips[positiveMod(activePage, clips.size)]
        if (clip.id == lastClipId) return@LaunchedEffect

        player.setMediaItem(MediaItem.fromUri(clip.videoUrl), true)
        player.prepare()
        player.playWhenReady = true

        lastClipId = clip.id
    }

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        beyondViewportPageCount = 1
    ) { virtualPage ->
        val clip = clips[positiveMod(virtualPage, clips.size)]
        val isActive = virtualPage == activePage

        ClipPage(
            clip = clip,
            player = if (isActive) player else null
        )
    }
}

private fun calcActivePage(currentPage: Int, offset: Float): Int {
    return when {
        offset > 0.2 -> currentPage + 1
        offset < -0.2 -> currentPage - 1
        else -> currentPage
    }
}


private fun positiveMod(value: Int, mod: Int): Int {
    val r = value % mod
    return if (r < 0) r + mod else r
}