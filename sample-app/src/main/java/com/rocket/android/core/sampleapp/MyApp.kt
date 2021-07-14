package com.rocket.android.core.sampleapp

import android.app.Application
import com.rocket.android.core.crashreporting.file.reporter.FileLogReporter
import com.rocket.core.domain.di.CoreProvider
import com.rocket.core.domain.di.CoreProvider.CoreProviderProperty

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CoreProvider.properties = mapOf(
            CoreProviderProperty.PRINT_LOGS to true
        )
        FileLogReporter.initialize(this)
    }
}
