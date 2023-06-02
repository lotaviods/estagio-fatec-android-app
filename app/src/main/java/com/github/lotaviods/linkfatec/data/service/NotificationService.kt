package com.github.lotaviods.linkfatec.data.service

import com.github.lotaviods.linkfatec.data.remote.model.Notification
import retrofit2.Response
import retrofit2.http.GET

interface NotificationService {
    @GET("v1/mobile/user/notification")
    suspend fun getNotifications(): Response<List<Notification>>
}