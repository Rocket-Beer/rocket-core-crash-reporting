package com.rocket.android.core.crashreporting.file.di

import android.app.Application
import com.rocket.android.core.crashreporting.file.printer.FileLogPrinter
import com.rocket.core.crashreporting.printer.LogPrinter
import com.rocket.core.domain.di.CoreProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Suppress("unused")
class CoreAndroidFileCrashProvider private constructor(private val application: Application) :
    CoreProvider() {

    val fileDispatcher: CoroutineDispatcher = Dispatchers.IO

    val logPrinter: LogPrinter by lazy {
        FileLogPrinter(application, fileDispatcher)
    }

    companion object {

        @Suppress("ObjectPropertyName")
        private lateinit var _instance: CoreAndroidFileCrashProvider

        fun getInstance(application: Application): CoreAndroidFileCrashProvider =
            synchronized(this) {
                if (Companion::_instance.isInitialized) {
                    _instance
                } else {
                    _instance = CoreAndroidFileCrashProvider(application)
                    _instance
                }
            }
    }
}
