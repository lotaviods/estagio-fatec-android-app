package com.github.lotaviods.linkfatec.data.remote.interceptor

import com.github.lotaviods.linkfatec.data.repository.interfaces.UserRepository
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(val repository: UserRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = repository.getUser().accessToken
        if (token.isNullOrEmpty()) return chain.proceed(chain.request())

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${repository.getUser().accessToken}")
            .build()
        return chain.proceed(request)
    }
}