package com.bbbrrr8877.totalrecall

import android.app.Application
import com.google.firebase.FirebaseApp

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}