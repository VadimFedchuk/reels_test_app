package com.example.instagramtestapp.core.connect

interface ConnectivityObserver {
    enum class Status { Available, Unavailable, Lost }

    val status: kotlinx.coroutines.flow.StateFlow<Status>
    fun refresh()
}