package com.github.lotaviods.linkfatec.ui.modules.notifications.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppNotificationsViewModel : ViewModel() {
    fun loadNotifications() = viewModelScope.launch {
        // TODO: Load notifications
    }

    private val mUiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState.Loading
    )
    val uiState: StateFlow<UiState> = mUiState

    sealed interface UiState {
        object Loaded : UiState

        object Loading : UiState

        object LoadedEmpty : UiState

        object Error : UiState
    }
}