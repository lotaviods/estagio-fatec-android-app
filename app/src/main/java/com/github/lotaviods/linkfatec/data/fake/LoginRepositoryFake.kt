package com.github.lotaviods.linkfatec.data.fake

import com.github.lotaviods.linkfatec.data.repository.LoginRepository
import com.github.lotaviods.linkfatec.model.Course
import com.github.lotaviods.linkfatec.model.LoginState
import com.github.lotaviods.linkfatec.model.User
import kotlinx.coroutines.delay

class LoginRepositoryFake : LoginRepository {
    private val mUser = User(
        1,
        "Luiz Otávio da Silva Carvalho",
        Course("Analise e desenvolvimento de sistemas"),
        "123456789"
    )

    override suspend fun login(user: String, password: String): LoginState {
        delay(5000)

        if (user == "luiz" && password == "123") return LoginState.Approved(mUser)

        return LoginState.Error
    }

    override suspend fun getLoginState(): LoginState {
        delay(500)
        return LoginState.Approved(mUser)
    }
}