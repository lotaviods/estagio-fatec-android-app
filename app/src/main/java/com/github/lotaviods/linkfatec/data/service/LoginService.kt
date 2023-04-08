package com.github.lotaviods.linkfatec.data.service

import com.github.lotaviods.linkfatec.data.remote.client.StudentLogin
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<StudentLogin>
}