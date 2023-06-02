package com.github.lotaviods.linkfatec.data.remote.repository

import com.github.lotaviods.linkfatec.data.remote.client.LoginWebClient
import com.github.lotaviods.linkfatec.data.remote.response.ApplicationResponse.Status
import com.github.lotaviods.linkfatec.data.remote.response.ApplicationResponse.Status.SUCCESS
import com.github.lotaviods.linkfatec.data.repository.interfaces.LoginRepository
import com.github.lotaviods.linkfatec.data.repository.interfaces.UserRepository
import com.github.lotaviods.linkfatec.model.Course
import com.github.lotaviods.linkfatec.model.LoginState
import com.github.lotaviods.linkfatec.model.User

class LoginRepositoryImpl(
    private val webClient: LoginWebClient,
    private val userRepository: UserRepository
) : LoginRepository {
    override suspend fun login(user: String, password: String): LoginState {
        try {
            val response = webClient.login(user, password)
            val student = response.data?.data

            if (response.isSuccessful && response.status == SUCCESS) {
                return LoginState.Approved(
                    User(
                        id = student?.id ?: -1,
                        name = student?.name ?: "",
                        course = student?.course ?: Course(-1, ""),
                        ra = student?.ra ?: "",
                        profilePicture = student?.profilePicture,
                        accessToken = response.data?.token?.accessToken
                    )
                )
            }

            if (response.status == Status.BAD_REQUEST)
                return LoginState.Declined

            return LoginState.Error
        } catch (e: Exception) {
            e.printStackTrace()
            return LoginState.Error
        }
    }

    override suspend fun getLoginState(): LoginState {
        val user = userRepository.getUser()

        if (user.id == -1)
            return LoginState.NotLogged

        return LoginState.Approved(user)
    }
}