package com.rocket.android.core.crashreporting.di

import android.content.Context
import com.rocket.android.core.crashreporting.printer.AndroidLogPrinter
import com.rocket.core.crashreporting.logger.CrashLogger
import com.rocket.core.crashreporting.logger.DefaultLogger

@Suppress("unused")
class CoreAndroidCrashProvider private constructor(private val context: Context) {

    val crashLogger: CrashLogger by lazy {
        DefaultLogger(debuggable = true, printer = AndroidLogPrinter())
    }

    companion object {

        @Suppress("ObjectPropertyName")
        private lateinit var _instance: CoreAndroidCrashProvider

        fun getInstance(context: Context): CoreAndroidCrashProvider =
            synchronized(this) {
                if (::_instance.isInitialized) {
                    _instance
                } else {
                    _instance = CoreAndroidCrashProvider(context = context)
                    _instance
                }
            }
    }
}
