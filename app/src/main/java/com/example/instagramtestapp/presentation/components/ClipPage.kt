package com.example.instagramtestapp.presentation.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.instagramtestapp.domain.model.Clip

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ClipPage(
    clip: Clip,
    player: ExoPlayer?,
    isActive: Boolean,
    onTogglePlayPause: () -> Unit,
    tapIcon: TapIcon?,
    onTapIconConsumed: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(isActive, player) {
                detectTapGestures {
                    if (isActive && player != null) onTogglePlayPause()
                }
            }
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PlayerView(ctx).apply {
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    this.player = player
                }
            },
            update = { view ->
                view.player = player
            }
        )

        ClipOverlay(clip = clip)
        PlayPauseFlashOverlay(
            trigger = tapIcon,
            onConsumed = onTapIconConsumed
        )
    }
}