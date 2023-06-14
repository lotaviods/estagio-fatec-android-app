package com.github.lotaviods.linkfatec.data.remote.interceptor

import android.content.Context
import android.content.Intent
import com.github.lotaviods.linkfatec.MainActivity
import com.github.lotaviods.linkfatec.data.repository.interfaces.UserRepository
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TokenInterceptor(val repository: UserRepository) : Interceptor, KoinComponent {
    private val context: Context by inject()
    private val userRepository: UserRepository by inject()
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = repository.getUser().accessToken
        if (token.isNullOrEmpty()) return chain.proceed(chain.request())

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${repository.getUser().accessToken}")
            .build()
        val response = chain.proceed(request)

        if (response.code == 401) {
            val user = userRepository.getUser()
            userRepository.deleteUser(user)

            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        return response
    }
}