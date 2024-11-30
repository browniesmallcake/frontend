package com.example.idiotchefassistant
import android.app.Application

class MyApp:  Application() {
    override fun onCreate() {
        super.onCreate()
        AuthTokenManager.initialize(applicationContext)
    }
}