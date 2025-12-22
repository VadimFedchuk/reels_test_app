package com.example.instagramtestapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.instagramtestapp.domain.model.Clip

@Composable
fun ClipOverlay(
    clip: Clip,
    modifier: Modifier = Modifier,
    onLikeClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BottomTitlePill(
            title = clip.title,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 8.dp)
        )

        RightActionsPill(
            likes = clip.likes,
            shares = clip.shares,
            onLikeClick = onLikeClick,
            onShareClick = onShareClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 6.dp)
        )
    }
}

@Composable
private fun BottomTitlePill(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Color.Black.copy(alpha = 0.55f))
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun RightActionsPill(
    likes: String,
    shares: String,
    onLikeClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Color.Black.copy(alpha = 0.45f))
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        ActionItem(
            icon = Icons.Outlined.FavoriteBorder,
            count = likes,
            onClick = onLikeClick
        )

        ActionItem(
            icon = Icons.Outlined.Share,
            count = shares,
            onClick = onShareClick
        )
    }
}

@Composable
private fun ActionItem(
    icon: ImageVector,
    count: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = count,
            color = Color.White.copy(alpha = 0.9f),
            style = MaterialTheme.typography.labelMedium
        )
    }
}
