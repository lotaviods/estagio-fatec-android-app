package com.github.lotaviods.linkfatec.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileWebService {
    @POST("mobile/student/resume")
    @Multipart
    suspend fun sendProfileResume(
        @Part("student_id") studentId: Int,
        @Part file: MultipartBody.Part,
    ): Response<ResponseBody>
}