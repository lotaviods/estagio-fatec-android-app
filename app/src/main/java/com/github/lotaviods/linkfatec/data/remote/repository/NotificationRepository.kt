package com.github.lotaviods.linkfatec.data.remote.repository

import com.github.lotaviods.linkfatec.data.remote.client.NotificationWebClient
import com.github.lotaviods.linkfatec.data.remote.model.Notification
import com.github.lotaviods.linkfatec.model.ErrorState
import com.github.lotaviods.linkfatec.resource.AppResource

class NotificationRepository(
    private val webClient: NotificationWebClient
) {

    suspend fun getAllNotifications(): AppResource<List<Notification>> {
        try {
            val response = webClient.getAllNotifications()

            if (response.isSuccessful) {
                return AppResource(response.data, false)
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