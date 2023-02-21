package com.github.lotaviods.linkfatec.data.remote.repository

import com.github.lotaviods.linkfatec.data.repository.LoginRepository
import com.github.lotaviods.linkfatec.model.LoginState

class LoginRepositoryImpl: LoginRepository {
    override suspend fun login(user: String, password: String): LoginState {
        TODO("Not yet implemented")
    }

    override suspend fun getLoginState(): LoginState {
        TODO("Not yet implemented")
    }
}