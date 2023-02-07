package com.github.lotaviods.linkfatec.model

sealed class LoginState {
    class Approved(val user: User): LoginState()
    object NotLogged: LoginState()
    object Declined : LoginState()
    object Error : LoginState()
}