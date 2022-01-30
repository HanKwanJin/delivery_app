package com.han.delivery_app

import android.app.Application
import com.han.delivery_app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class TrackerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE) //kotlin 1.6 에서 동작하지 않으므로 androidLogger(Level.NONE)으로 처리함
            androidContext(this@TrackerApplication)
            modules(appModule)
        }
    }

}