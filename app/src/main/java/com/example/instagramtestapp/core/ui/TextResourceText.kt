package com.example.instagramtestapp.core.ui

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.instagramtestapp.core.ui.uiUtils.TextResource

@Composable
fun TextResourceText(
    text: TextResource?,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val value = remember(text) { text.asString(context) }

    if (!value.isNullOrBlank()) {
        Text(
            text = value,
            modifier = modifier
        )
    }
}

private fun TextResource?.asString(context: Context): String? = when (this) {
    null -> null
    is TextResource.StringResource -> context.getString(resId)
    is TextResource.DynamicString -> value
}