package com.example.instagramtestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramtestapp.R
import com.example.instagramtestapp.core.connect.ConnectivityObserver
import com.example.instagramtestapp.domain.model.Clip
import com.example.instagramtestapp.domain.repository.ClipsRepository
import com.example.instagramtestapp.core.ui.uiUtils.TextResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ClipsRepository,
    private val connectivity: ConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(ClipsUiState(isLoading = true))
    val state: StateFlow<ClipsUiState> = _state

    init {
        viewModelScope.launch {
            connectivity.status.collect { s ->
                if (s != ConnectivityObserver.Status.Available) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = TextResource.StringResource(R.string.no_internet_connection_error))
                    }
                }
                else {
                    _state.update { it.copy(errorMessage = null) }
                    loadClips()
                }
            }
        }
    }


    fun tryAgain() {
        connectivity.refresh()
        loadClips()
    }

    private fun loadClips() {
        viewModelScope.launch {
            if (connectivity.status.value != ConnectivityObserver.Status.Available) {
                _state.update { it.copy(errorMessage = TextResource.StringResource(R.string.no_internet_connection_error)) }
                return@launch
            }
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            repository.getFeeds()
                .onSuccess { feed ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            feedTitle = feed.feedTitle,
                            clips = feed.clips
                        )
                    }
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message?.let { msg -> TextResource.DynamicString(msg) }
                                ?: TextResource.StringResource(R.string.unknown_error)
                        )
                    }
                }
        }
    }
}

data class ClipsUiState(
    val isLoading: Boolean = false,
    val feedTitle: String = "",
    val clips: List<Clip> = emptyList(),
    val errorMessage: TextResource? = null
)