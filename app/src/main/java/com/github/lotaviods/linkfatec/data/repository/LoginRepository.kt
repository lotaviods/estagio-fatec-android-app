package com.github.lotaviods.linkfatec.data.repository

import com.github.lotaviods.linkfatec.model.LoginState

interface LoginRepository {
    suspend fun login(user: String, password: String): LoginState

    suspend fun getLoginState(): LoginState
}