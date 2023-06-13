package com.github.lotaviods.linkfatec.data.remote.client

import com.github.lotaviods.linkfatec.data.remote.response.ApplicationResponse
import com.github.lotaviods.linkfatec.data.remote.retrofit.RetrofitClient
import com.github.lotaviods.linkfatec.data.service.DeviceService
import okhttp3.ResponseBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DeviceWebClient: BaseWebClient(), KoinComponent {
    private val service: DeviceService by lazy {
        get<RetrofitClient>().getService()
    }

    suspend fun registerDevice(uuid: String, description: String, token: String): ApplicationResponse<ResponseBody> {
        return execute { service.device(uuid, description, token) }
    }
}