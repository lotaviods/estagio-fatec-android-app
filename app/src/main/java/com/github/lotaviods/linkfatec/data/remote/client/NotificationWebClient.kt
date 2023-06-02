package com.github.lotaviods.linkfatec.data.remote.client

import com.github.lotaviods.linkfatec.data.remote.model.Notification
import com.github.lotaviods.linkfatec.data.remote.response.ApplicationResponse
import com.github.lotaviods.linkfatec.data.remote.retrofit.RetrofitClient
import com.github.lotaviods.linkfatec.data.service.NotificationService
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class NotificationWebClient : BaseWebClient(), KoinComponent {
    private val service: NotificationService by lazy {
        get<RetrofitClient>().getService()
    }

    suspend fun getAllNotifications(): ApplicationResponse<List<Notification>> {
        return execute { service.getNotifications() }
    }
}