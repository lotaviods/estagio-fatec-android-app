package com.github.lotaviods.linkfatec.ui.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lotaviods.linkfatec.data.repository.interfaces.LoginRepository
import com.github.lotaviods.linkfatec.data.repository.interfaces.UserRepository
import com.github.lotaviods.linkfatec.model.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApplicationViewModel(
    private val repository: LoginRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val mUiState: MutableStateFlow<ApplicationState> =
        MutableStateFlow(ApplicationState.Loading)

    val uiState: StateFlow<ApplicationState> = mUiState


    fun getLoginState() = viewModelScope.launch {
        when (val loginState = repository.getLoginState()) {
            is LoginState.Approved -> {
                userRepository.saveUser(loginState.user)
                mUiState.emit(ApplicationState.Logged)
            }
            else -> {
                mUiState.emit(ApplicationState.LoginState)
            }
        }
    }

    sealed class ApplicationState {
        object LoginState : ApplicationState()
        object Logged : ApplicationState()
        object Loading : ApplicationState()
    }

}