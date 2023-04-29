package com.github.lotaviods.linkfatec.data.service

import com.github.lotaviods.linkfatec.data.remote.model.StudentUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProfileWebService {
    @POST("v1/mobile/student/resume")
    @Multipart
    suspend fun sendProfileResume(
        @Part("student_id") studentId: Int,
        @Part file: MultipartBody.Part,
    ): Response<ResponseBody>

    @POST("v1/mobile/student/profile_picture")
    @Multipart
    suspend fun sendProfilePicture(
        @Part("student_id") studentId: Int,
        @Part file: MultipartBody.Part,
    ): Response<ResponseBody>

    @GET("v1/student/{student_id}/detail")
    suspend fun getUserProfile(@Path("student_id") studentId: Int): Response<StudentUser>

}