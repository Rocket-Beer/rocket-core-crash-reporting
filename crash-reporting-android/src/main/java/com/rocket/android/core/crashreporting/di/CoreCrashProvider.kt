package com.rocket.android.core.crashreporting.di

import android.content.Context
import com.rocket.android.core.crashreporting.logger.DefaultLogger
import com.rocket.android.core.crashreporting.printer.DefaultLogPrinter
import com.rocket.core.crashreporting.logger.CrashLogger

@Suppress("unused")
class CoreCrashProvider private constructor(private val context: Context) {

    val crashLogger: CrashLogger by lazy {
        DefaultLogger(debuggable = true, printer = DefaultLogPrinter())
    }

    companion object {

        @Suppress("ObjectPropertyName")
        private lateinit var _instance: CoreCrashProvider

        fun getInstance(context: Context): CoreCrashProvider =
            synchronized(this) {
                if (::_instance.isInitialized) {
                    _instance
                } else {
                    _instance = CoreCrashProvider(context = context)
                    _instance
                }
            }
    }
}
