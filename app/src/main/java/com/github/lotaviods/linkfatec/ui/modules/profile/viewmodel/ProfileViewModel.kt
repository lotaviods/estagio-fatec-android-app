package com.github.lotaviods.linkfatec.ui.modules.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lotaviods.linkfatec.data.repository.ProfileRepository
import com.github.lotaviods.linkfatec.data.repository.UserRepository
import com.github.lotaviods.linkfatec.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val mUiState: MutableSharedFlow<UiState> = MutableSharedFlow()
    val uiState: SharedFlow<UiState> = mUiState

    fun logoutUser(user: User?) {
        user?.let { profile -> userRepository.deleteUser(profile) }
    }

    fun sendProfileResume(pdfBytes: ByteArray?) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val resp = profileRepository.sendProfileResume(userRepository.getUser().id, pdfBytes)

            if (resp.hasError) {
                mUiState.emit(UiState.Error)
                return@withContext
            }
            mUiState.emit(UiState.Success)
        }
    }

    fun sendProfilePicture(bytes: ByteArray?) = viewModelScope.launch{
        withContext(Dispatchers.IO) {
            val resp = profileRepository.sendProfilePicture(userRepository.getUser().id, bytes)

            if (resp.hasError) {
                mUiState.emit(UiState.Error)
                return@withContext
            }
            mUiState.emit(UiState.Success)
        }
    }

    fun getUser(): User {
        return userRepository.getUser()
    }

    sealed interface UiState {
        object Success : UiState

        object Error : UiState
    }
}