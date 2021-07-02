package com.rocket.android.core.crashreporting.logger

import com.rocket.android.core.crashreporting.printer.DefaultLogPrinter
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.lang.Exception

internal class DefaultLoggerTest {
    @MockK
    lateinit var logPrinter: DefaultLogPrinter

    private lateinit var defaultCrashLogger: DefaultLogger

    @Test
    fun `crashlogger with debuggable disabled doesnt log messages`() {
        logPrinter = spyk(DefaultLogPrinter())
        defaultCrashLogger = DefaultLogger(debuggable = false, logPrinter)

        defaultCrashLogger.log(message = "message")

        verify(exactly = 0) {
            logPrinter.printMessage(any(), any(), any())
        }
    }

    @Test
    fun `crashlogger with debuggable disabled doesnt log exceptions`() {
        logPrinter = spyk(DefaultLogPrinter())
        defaultCrashLogger = DefaultLogger(debuggable = false, logPrinter)

        defaultCrashLogger.log(exception = Exception("exception message"))

        verify(exactly = 0) {
            logPrinter.printMessage(any(), any(), any())
        }
    }

    @Test
    fun `crashlogger with debuggable enabled log messages`() {
        logPrinter = spyk(DefaultLogPrinter())
        defaultCrashLogger = DefaultLogger(debuggable = true, logPrinter)

        defaultCrashLogger.log(message = "message")

        verify(exactly = 1) {
            logPrinter.printMessage(any(), any(), any())
        }
    }

    @Test
    fun `crashlogger with debuggable enabled log exceptions`() {
        logPrinter = spyk(DefaultLogPrinter())
        defaultCrashLogger = DefaultLogger(debuggable = true, logPrinter)

        defaultCrashLogger.log(exception = Exception("exception message"))

        verify(exactly = 1) {
            logPrinter.printMessage(any(), any(), any())
        }
    }
}
