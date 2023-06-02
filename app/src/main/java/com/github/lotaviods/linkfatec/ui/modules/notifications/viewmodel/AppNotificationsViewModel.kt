package com.github.lotaviods.linkfatec.ui.modules.notifications.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lotaviods.linkfatec.data.remote.model.JobNotificationModel
import com.github.lotaviods.linkfatec.data.remote.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppNotificationsViewModel(
    val repository: NotificationRepository
) : ViewModel() {
    private val mUiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState.Loading
    )
    val uiState: StateFlow<UiState> = mUiState

    init {
        loadNotifications()
    }

    fun loadNotifications() = viewModelScope.launch {
        mUiState.emit(UiState.Loading)

        val resp = repository.getAllNotifications()

        if (resp.hasError) {
            mUiState.emit(UiState.Error)
            return@launch
        }
        if (resp.data.isNullOrEmpty()) {
            mUiState.emit(UiState.LoadedEmpty)
            return@launch
        }
        val data = resp.data.filterIsInstance<JobNotificationModel>()

        if (data.isEmpty()) {
            mUiState.emit(UiState.LoadedEmpty)
            return@launch
        }

        mUiState.emit(UiState.Loaded(data))
    }

    sealed interface UiState {
        data class Loaded(val items: List<JobNotificationModel>) : UiState

        object Loading : UiState

        object LoadedEmpty : UiState

        object Error : UiState
    }
}