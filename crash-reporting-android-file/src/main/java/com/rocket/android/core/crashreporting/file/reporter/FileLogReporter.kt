package com.rocket.android.core.crashreporting.file.reporter

import android.app.Application
import com.rocket.android.core.crashreporting.file.di.CoreAndroidFileCrashProvider
import com.rocket.core.crashreporting.logger.LogLevel
import com.rocket.core.crashreporting.printer.LogPrinter
import com.rocket.core.domain.di.CoreProvider.CoreProviderProperty

@Suppress("UnusedPrivateMember")
class FileLogReporter private constructor(application: Application) {
    private val debuggable: Boolean
    private val fileLogPrinter: LogPrinter

    init {
        CoreAndroidFileCrashProvider.getInstance(application).run {
            debuggable = getPropertyOrNull<Boolean>(CoreProviderProperty.PRINT_LOGS) ?: false
            fileLogPrinter = logPrinter
        }

        application.mainLooper.thread.setUncaughtExceptionHandler { thread, throwable ->
            if (debuggable) {
                fileLogPrinter.printMessage(
                    "FileLogReporter setUncaughtExceptionHandler",
                    throwable.stackTraceToString(),
                    logLevel = LogLevel.ERROR
                )
            }
        }

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            if (debuggable) {
                fileLogPrinter.printMessage(
                    "FileLogReporter setDefaultUncaughtExceptionHandler",
                    throwable.localizedMessage ?: "",
                    logLevel = LogLevel.ERROR
                )
            }
        }
    }

    companion object {
        fun initialize(application: Application): FileLogReporter {
            return FileLogReporter(application = application)
        }
    }
}
