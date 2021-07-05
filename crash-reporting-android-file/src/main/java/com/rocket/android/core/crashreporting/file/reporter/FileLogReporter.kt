package com.rocket.android.core.crashreporting.file.reporter

import android.app.Application
import com.rocket.android.core.crashreporting.file.printer.FileLogPrinter
import com.rocket.core.crashreporting.logger.LogLevel
import kotlinx.coroutines.CoroutineDispatcher

@Suppress("UnusedPrivateMember")
class FileLogReporter private constructor(
    private val fileLogPrinter: FileLogPrinter
) {
    fun printMessage(tag: String, msg: String, logLevel: LogLevel) {
        fileLogPrinter.printMessage(tag, msg, logLevel)
    }

    companion object {
        fun initialize(application: Application, dispatcher: CoroutineDispatcher) {
            val fileReporter = FileLogReporter(FileLogPrinter(application, dispatcher))

            application.mainLooper.thread.setUncaughtExceptionHandler { thread, throwable ->
                fileReporter.printMessage(
                    "FileLogReporter setUncaughtExceptionHandler",
                    throwable.stackTraceToString(),
                    logLevel = LogLevel.ERROR
                )
            }

            Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                fileReporter.printMessage(
                    "FileLogReporter setDefaultUncaughtExceptionHandler",
                    throwable.localizedMessage ?: "",
                    logLevel = LogLevel.ERROR
                )
            }
        }
    }
}
