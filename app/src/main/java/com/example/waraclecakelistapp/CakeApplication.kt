package com.example.waraclecakelistapp

import android.app.Application
import com.example.waraclecakelistapp.di.AppContainer

class CakeApplication: Application() {

    val appContainer: AppContainer by lazy {
        AppContainer()
    }
}