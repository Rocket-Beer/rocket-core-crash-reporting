package com.rocket.android.core.crashreporting.di

import android.content.Context
import com.rocket.android.core.crashreporting.printer.AndroidLogPrinter
import com.rocket.core.crashreporting.logger.CrashLogger
import com.rocket.core.crashreporting.logger.DefaultLogger
import com.rocket.core.crashreporting.printer.LogPrinter
import com.rocket.core.domain.di.CoreProvider

@Suppress("unused")
class CoreAndroidCrashProvider private constructor(private val context: Context) : CoreProvider() {

    val logPrinter: LogPrinter by lazy {
        AndroidLogPrinter()
    }

    val crashLogger: CrashLogger by lazy {
        DefaultLogger(
            debuggable = getPropertyOrNull<Boolean>(CoreProviderProperty.PRINT_LOGS) ?: false,
            printer = logPrinter
        )
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
