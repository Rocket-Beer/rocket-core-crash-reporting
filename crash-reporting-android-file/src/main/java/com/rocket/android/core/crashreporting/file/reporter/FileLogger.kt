package com.rocket.android.core.crashreporting.file.reporter

import android.app.Application
import com.rocket.android.core.crashreporting.file.printer.FileLogPrinter

class FileLogger private constructor(private val fileLogPrinter: FileLogPrinter) {

    init {

    }

    companion object {
        fun initialize(application: Application) {
            FileLogger(FileLogPrinter(application))

            application.mainLooper.thread.setUncaughtExceptionHandler { thread, throwable ->

            }

            Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->


            }
        }
    }
}