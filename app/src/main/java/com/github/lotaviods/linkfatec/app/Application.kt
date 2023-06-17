package com.github.lotaviods.linkfatec.app

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.github.lotaviods.linkfatec.data.remote.repository.DeviceRepository
import com.github.lotaviods.linkfatec.di.AppModules
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class Application : Application(), KoinComponent {
    private val deviceRepository: DeviceRepository by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@Application)
            // use modules
            modules(
                AppModules.modules
            )
        }
        setUpNotificationChannels()
    }

    private fun setUpNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel("default", "default", NotificationManager.IMPORTANCE_HIGH)
            )
            val manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannels(channels)
        }
    }

    @SuppressLint("HardwareIds")
    fun registerPush() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val uuid = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

            CoroutineScope(Dispatchers.IO).launch {
                deviceRepository.registerDevice(uuid, Build.MODEL, task.result)
            }
        })
    }

    companion object {
        private const val TAG = "Application"
    }
}