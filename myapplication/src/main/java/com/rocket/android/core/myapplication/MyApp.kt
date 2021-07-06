package com.rocket.android.core.myapplication

import android.app.Application
import com.rocket.android.core.crashreporting.file.reporter.FileLogReporter
import kotlinx.coroutines.Dispatchers

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FileLogReporter.initialize(this, Dispatchers.IO)
    }
}
