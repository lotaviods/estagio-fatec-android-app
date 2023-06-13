package com.github.lotaviods.linkfatec.data.remote.repository

import com.github.lotaviods.linkfatec.data.remote.client.DeviceWebClient
import com.github.lotaviods.linkfatec.model.ErrorState
import com.github.lotaviods.linkfatec.resource.AppResource

class DeviceRepository(private val webClient: DeviceWebClient){

    suspend fun registerDevice(uuid: String, description: String, token: String): AppResource<Any> {
        try {
            val response = webClient.registerDevice(uuid, description, token)

            if (response.isSuccessful) {
                return AppResource(null, false)
            }
            return AppResource(
                null,
                true,
                ErrorState.InternetConnection
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return AppResource(
                null, true,
                ErrorState.Unexpected
            )
        }

    }
}