package com.github.lotaviods.linkfatec.ui.modules.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lotaviods.linkfatec.data.repository.interfaces.ProfileRepository
import com.github.lotaviods.linkfatec.data.repository.interfaces.UserRepository
import com.github.lotaviods.linkfatec.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val mUiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial(getUser()))
    val uiState: StateFlow<UiState> = mUiState

    private val mUiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = mUiEvent

    init {
        updateUserProfileInformation()
    }

    fun logoutUser(user: User?) {
        user?.let { profile -> userRepository.deleteUser(profile) }
    }

    fun sendProfileResume(pdfBytes: ByteArray?) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val resp = profileRepository.sendProfileResume(userRepository.getUser().id, pdfBytes)

            if (resp.hasError) {
                mUiEvent.emit(UiEvent.Error)
                return@withContext
            }
            mUiEvent.emit(UiEvent.Success)
        }
    }

    fun sendProfilePicture(bytes: ByteArray?) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val resp = profileRepository.sendProfilePicture(userRepository.getUser().id, bytes)

            if (resp.hasError) {
                mUiEvent.emit(UiEvent.Error)
                return@withContext
            }
            mUiEvent.emit(UiEvent.Success)
            updateUserProfileInformation()
        }
    }

    private fun getUser(): User {
        return userRepository.getUser()
    }

    private fun updateUserProfileInformation() = viewModelScope.launch {
        val user = userRepository.getUpdatedUserInformation()
        mUiState.emit(UiState.Initial(user))
    }

    sealed interface UiEvent {
        object Success : UiEvent

        object Error : UiEvent
    }

    sealed class UiState(open val user: User) {
        class Initial(override val user: User) : UiState(user)
    }
}