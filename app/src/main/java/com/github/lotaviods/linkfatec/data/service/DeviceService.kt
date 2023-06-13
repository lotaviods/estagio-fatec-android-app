package com.github.lotaviods.linkfatec.data.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DeviceService {

    @POST("v1/mobile/device")
    @FormUrlEncoded
    suspend fun device(
        @Field("uuid") uuid: String,
        @Field("description") description: String,
        @Field("token") deviceToken: String
    ): Response<ResponseBody>
}