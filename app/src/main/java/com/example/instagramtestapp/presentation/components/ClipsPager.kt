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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.instagramtestapp.domain.model.Clip


/*
    Most of the ExoPlayer logic in this flow was implemented with the help of ChatGPT.
    I haven’t worked with this kind of multi-player pager/video flow before, and due to limited time
    I focused on finding a reliable and maintainable solution rather than experimenting with different
     approaches from scratch.
 */
@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClipsPager(clips: List<Clip>) {
    if (clips.isEmpty()) return

    val context = LocalContext.current

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
            calculateActivePage(
                currentPage = pagerState.currentPage,
                offset = pagerState.currentPageOffsetFraction
            )
        }
    }

    val players = remember { mutableStateMapOf<Int, ExoPlayer>() }
    val boundClipId = remember { mutableStateMapOf<Int, String>() }

    fun createPlayer(): ExoPlayer {
        return ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
            volume = 1f
        }
    }

    fun clipFor(page: Int): Clip {
        return clips[positiveMod(page, clips.size)]
    }

    fun bindMediaIfNeeded(page: Int, play: Boolean) {
        val player = players.getOrPut(page) { createPlayer() }
        val clip = clipFor(page)

        val lastId = boundClipId[page]
        if (lastId != clip.id) {
            player.setMediaItem(MediaItem.fromUri(clip.videoUrl), true)
            player.prepare()
            boundClipId[page] = clip.id
        }

        player.playWhenReady = play
        if (!play) player.pause()
    }

    LaunchedEffect(activePage, clips) {
        val keep = setOf(activePage - 1, activePage, activePage + 1)

        bindMediaIfNeeded(activePage, play = true)
        bindMediaIfNeeded(activePage - 1, play = false)
        bindMediaIfNeeded(activePage + 1, play = false)

        val toRemove = players.keys - keep
        toRemove.forEach { page ->
            players.remove(page)?.release()
            boundClipId.remove(page)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            players.values.forEach { it.release() }
            players.clear()
            boundClipId.clear()
        }
    }

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        beyondViewportPageCount = 1
    ) { virtualPage ->
        val clip = clipFor(virtualPage)

        val playerForPage = players[virtualPage]

        ClipPage(
            clip = clip,
            player = playerForPage
        )
    }
}

private fun calculateActivePage(currentPage: Int, offset: Float): Int {
    return when {
        offset > 0.2f -> currentPage + 1
        offset < -0.2f -> currentPage - 1
        else -> currentPage
    }
}

private fun positiveMod(value: Int, mod: Int): Int {
    val r = value % mod
    return if (r < 0) r + mod else r
}