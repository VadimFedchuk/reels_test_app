package com.example.instagramtestapp.core.ui.uiUtils

sealed class TextResource {
    data class DynamicString(val value: String) : TextResource()
    data class StringResource(val resId: Int) : TextResource()
}