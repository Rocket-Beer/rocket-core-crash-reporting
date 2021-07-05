package com.rocket.core.crashreporting.di

import com.rocket.core.crashreporting.logger.CrashLogger
import com.rocket.core.crashreporting.logger.DefaultLogger
import com.rocket.core.crashreporting.printer.DefaultLogPrinter

@Suppress("unused")
class CoreCrashProvider private constructor() {

    val crashLogger: CrashLogger by lazy {
        DefaultLogger(debuggable = true, printer = DefaultLogPrinter())
    }

    companion object {

        @Suppress("ObjectPropertyName")
        private lateinit var _instance: CoreCrashProvider

        fun getInstance(): CoreCrashProvider =
            synchronized(this) {
                if (::_instance.isInitialized) {
                    _instance
                } else {
                    _instance = CoreCrashProvider()
                    _instance
                }
            }
    }
}
