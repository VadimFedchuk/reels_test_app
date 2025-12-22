package com.example.instagramtestapp.core.connect

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConnectivityObserverImpl(
    context: Context
) : ConnectivityObserver {

    private val appContext = context.applicationContext
    private val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _status = MutableStateFlow(currentStatus())
    override val status: StateFlow<ConnectivityObserver.Status> = _status

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            scope.launch { _status.value = ConnectivityObserver.Status.Available }
        }

        override fun onLosing(network: Network, maxMsToLive: Int) { }

        override fun onLost(network: Network) {
            scope.launch { _status.value = ConnectivityObserver.Status.Lost }
        }

        override fun onUnavailable() {
            scope.launch { _status.value = ConnectivityObserver.Status.Unavailable }
        }
    }

    init {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(request, callback)
    }

    override fun refresh() {
        _status.value = currentStatus()
    }

    private fun currentStatus(): ConnectivityObserver.Status {
        val network = cm.activeNetwork ?: return ConnectivityObserver.Status.Unavailable
        val caps = cm.getNetworkCapabilities(network) ?: return ConnectivityObserver.Status.Unavailable
        val hasInternet = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        return if (hasInternet) ConnectivityObserver.Status.Available else ConnectivityObserver.Status.Unavailable
    }
}