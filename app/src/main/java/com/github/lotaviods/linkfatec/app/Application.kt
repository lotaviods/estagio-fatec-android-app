package com.github.lotaviods.linkfatec.app

import android.app.Application
import com.github.lotaviods.linkfatec.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
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
    }
}