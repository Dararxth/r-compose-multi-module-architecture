package com.rxth.multimodule

import android.app.Application
import com.rxth.multimodule.core.network.networkModule
import com.rxth.multimodule.feature.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            androidLogger()
            modules(
                listOf(
                    networkModule,
                    homeModule
                )
            )
        }
    }

}