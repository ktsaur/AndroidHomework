package ru.itis.homework6

import android.app.Application
import ru.itis.homework6.di.ServiceLocator

class App: Application() {
    private val serviceLocator = ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator.initDataLayerDependencies(ctx = this)
    }

}