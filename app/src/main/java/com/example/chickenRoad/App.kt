package com.example.chickenRoad

import android.app.Application
import com.example.chickenRoad.di.DataModule
import com.example.chickenRoad.di.ManagerModule
import com.example.chickenRoad.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                DataModule,
                ManagerModule,
                ViewModelModule
            )
        }
    }
}
