package com.github.lotaviods.linkfatec.service

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.component.KoinComponent


class MyFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    @SuppressLint("HardwareIds")
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}