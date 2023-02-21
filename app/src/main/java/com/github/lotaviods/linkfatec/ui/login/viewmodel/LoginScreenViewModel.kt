package com.github.lotaviods.linkfatec.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lotaviods.linkfatec.data.repository.LoginRepository
import com.github.lotaviods.linkfatec.data.repository.UserRepository
import com.github.lotaviods.linkfatec.model.LoginState
import com.github.lotaviods.linkfatec.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val repository: LoginRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val mUiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Initial)
    val uiState: StateFlow<LoginUiState> = mUiState

    fun login(user: String, password: String) = viewModelScope.launch {
        mUiState.emit(LoginUiState.Loading)

        when (val loginState = repository.login(user, password)) {
            is LoginState.Approved -> {
                userRepository.saveUser(loginState.user)

                mUiState.emit(
                    LoginUiState.Success(
                        loginState.user
                    )
                )
            }

            LoginState.Declined -> {
                mUiState.emit(LoginUiState.Declined)
            }

            LoginState.Error -> {
                mUiState.emit(LoginUiState.Error)
            }

            LoginState.NotLogged -> {
                mUiState.emit(LoginUiState.Declined)
            }
        }
    }

    sealed class LoginUiState {
        object Initial : LoginUiState()
        object Loading : LoginUiState()
        object Error : LoginUiState()
        data class Success(val profile: User) : LoginUiState()
        object Declined : LoginUiState()
    }
}