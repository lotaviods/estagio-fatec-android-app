package com.github.lotaviods.linkfatec.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.github.lotaviods.linkfatec.MainActivity
import com.github.lotaviods.linkfatec.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.component.KoinComponent
import kotlin.random.Random


class MyFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val pendingIntent = newPendingIntent()

        message.notification?.let {
            createChannel(it, pendingIntent).let { notification ->

                with(NotificationManagerCompat.from(this)) {
                    if (ActivityCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    notify(Random.nextInt(), notification)
                }

            }

        }
    }

    private fun newPendingIntent(): PendingIntent {
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivities(this, 0, arrayOf(intent), pendingIntentFlags)
    }

    @SuppressLint("HardwareIds")
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun createChannel(
        notification: RemoteMessage.Notification,
        pendingIntent: PendingIntent
    ): Notification {
        return NotificationCompat.Builder(this, notification.channelId ?: "default")
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(Uri.parse("android.resource://$packageName/raw/${notification.sound ?: "silence"}"))
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(notification.body)
            )
            .build()
    }
}