package com.example.instagramtestapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.instagramtestapp.R
import com.example.instagramtestapp.core.ui.TextResourceText
import com.example.instagramtestapp.presentation.MainViewModel
import com.example.instagramtestapp.presentation.components.ClipsPager
import org.koin.androidx.compose.koinViewModel

@Composable
fun ClipsScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val clips = state.clips

    Box(modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null -> {
                Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally ) {
                        TextResourceText(text = state.errorMessage)
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = viewModel::tryAgain) {
                            Text(stringResource(R.string.retry))
                        }
                    }
                }
            }

            clips.isNotEmpty() -> {
                ClipsPager(
                    clips = clips
                )
            }
        }

        if (state.feedTitle.isNotBlank()) {
            Text(
                text = state.feedTitle,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(top = 12.dp),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}
