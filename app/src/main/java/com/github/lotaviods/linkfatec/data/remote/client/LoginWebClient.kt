package com.github.lotaviods.linkfatec.data.remote.client

import com.github.lotaviods.linkfatec.data.remote.model.StudentLogin
import com.github.lotaviods.linkfatec.data.remote.response.ApplicationResponse
import com.github.lotaviods.linkfatec.data.remote.retrofit.RetrofitClient
import com.github.lotaviods.linkfatec.data.service.LoginService
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class LoginWebClient : BaseWebClient(), KoinComponent {
    private val service: LoginService by lazy {
        get<RetrofitClient>().getService()
    }


    suspend fun login(email: String, password: String): ApplicationResponse<StudentLogin> {
        return execute { service.login(email, password) }
    }

}