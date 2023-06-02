package com.github.lotaviods.linkfatec.data.remote.client

import com.github.lotaviods.linkfatec.data.remote.model.StudentUser
import com.github.lotaviods.linkfatec.data.remote.response.ApplicationResponse
import com.github.lotaviods.linkfatec.data.remote.response.ApplicationResponse.*
import com.github.lotaviods.linkfatec.data.remote.retrofit.RetrofitClient
import com.github.lotaviods.linkfatec.data.service.ProfileWebService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ProfileWebClient : BaseWebClient(), KoinComponent {
    private val service: ProfileWebService by lazy {
        get<RetrofitClient>().getService()
    }

    suspend fun sendProfileResume(studentId: Int, pdfBytes: ByteArray?): ApplicationResponse<ResponseBody> {
        val requestFile = pdfBytes?.let {
            it.toRequestBody("application/pdf".toMediaTypeOrNull(), 0, it.size)
        } ?: return ApplicationResponse(status = Status.UNDETERMINED)

        val filePart = MultipartBody.Part.createFormData("file", "file.pdf", requestFile)

        return execute { service.sendProfileResume(studentId, filePart) }
    }

    suspend fun sendProfilePicture(studentId: Int, bytes: ByteArray?): ApplicationResponse<ResponseBody> {
        val requestFile = bytes?.let {
            it.toRequestBody("application/pdf".toMediaTypeOrNull(), 0, it.size)
        } ?: return ApplicationResponse(status = Status.UNDETERMINED)

        val filePart = MultipartBody.Part.createFormData("file", "file.pdf", requestFile)

        return execute { service.sendProfilePicture(studentId, filePart) }
    }

    suspend fun getUserProfile(studentId: Int): ApplicationResponse<StudentUser> {
        return execute { service.getUserProfile(studentId) }
    }

}