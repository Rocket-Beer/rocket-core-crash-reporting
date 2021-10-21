package com.rocket.android.core.crashreporting.file.reporter

import android.app.Application
import android.util.Log
import com.rocket.android.core.crashreporting.file.di.CoreAndroidFileCrashProvider
import com.rocket.android.core.data.permissions.Permissions
import com.rocket.android.core.data.permissions.di.CoreDataProvider
import com.rocket.core.crashreporting.logger.LogLevel
import com.rocket.core.crashreporting.printer.LogPrinter
import com.rocket.core.domain.di.CoreProvider.CoreProviderProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("UnusedPrivateMember")
class FileLogReporter private constructor(private val application: Application) {

    private val debuggable: Boolean
    private val fileLogPrinter: LogPrinter

    init {
        CoreAndroidFileCrashProvider.getInstance(application).run {
            debuggable = getPropertyOrNull<Boolean>(CoreProviderProperty.PRINT_LOGS) ?: false
            fileLogPrinter = logPrinter
        }

        //registerMainThreadExceptionHandler()
        //registerBackgroundThreadExceptionHandler()
    }

    private fun registerMainThreadExceptionHandler() {
        application.mainLooper.thread.setUncaughtExceptionHandler { thread, throwable ->
            if (debuggable) {
                    fileLogPrinter.printMessage(
                        "FileLogReporter ${thread.name}",
                        throwable.stackTraceToString(),
                        logLevel = LogLevel.ERROR
                    )
            }
            //finishApplication()
        }
    }


    private fun registerBackgroundThreadExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                fileLogPrinter.printMessage(
                    "FileLogReporter ${thread.name}",
                    throwable.stackTraceToString(),
                    logLevel = LogLevel.ERROR
                )
            //finishApplication()
        }
    }

    private fun finishApplication() {
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    companion object {
        fun initialize(application: Application): FileLogReporter {
            return FileLogReporter(application = application)
        }
    }
}
