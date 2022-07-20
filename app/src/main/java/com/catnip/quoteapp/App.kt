package com.catnip.quoteapp

import android.app.Application
import com.catnip.core.di.CoreModule
import com.catnip.quoteapp.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.getModules() + CoreModule.getModules())
        }
    }
}