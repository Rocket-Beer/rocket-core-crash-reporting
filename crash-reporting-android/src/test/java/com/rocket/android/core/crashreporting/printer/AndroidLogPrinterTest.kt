package com.rocket.android.core.crashreporting.printer

import android.util.Log
import com.rocket.core.crashreporting.logger.LogLevel
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AndroidLogPrinterTest {
    private lateinit var androidLogPrinter: AndroidLogPrinter

    @BeforeEach
    fun setup() {
        mockkStatic(Log::class)

        androidLogPrinter = AndroidLogPrinter()
    }

    @Test
    fun `defaultLogPrinter with DEBUG logs debug`() {
        val tag = "tag"
        val msg = "message"
        val logLevel = LogLevel.DEBUG

        androidLogPrinter.printMessage(tag, msg, logLevel)

        every { Log.d(any(), any()) } returns mockk()

        verify(exactly = 1) {
            Log.d(tag, msg)
        }
    }

    @Test
    fun `defaultLogPrinter with INFO logs info`() {
        val tag = "tag"
        val msg = "message"
        val logLevel = LogLevel.INFO

        androidLogPrinter.printMessage(tag, msg, logLevel)

        every { Log.i(any(), any()) } returns mockk()

        verify(exactly = 1) {
            Log.i(tag, msg)
        }
    }

    @Test
    fun `defaultLogPrinter with WARN logs warn`() {
        val tag = "tag"
        val msg = "message"
        val logLevel = LogLevel.WARN

        androidLogPrinter.printMessage(tag, msg, logLevel)

        every { Log.w(any(), any<String>()) } returns mockk()

        verify(exactly = 1) {
            Log.w(tag, msg)
        }
    }

    @Test
    fun `defaultLogPrinter with ERROR logs debug`() {
        val tag = "tag"
        val msg = "message"
        val logLevel = LogLevel.ERROR

        androidLogPrinter.printMessage(tag, msg, logLevel)

        every { Log.e(any(), any()) } returns mockk()

        verify(exactly = 1) {
            Log.e(tag, msg)
        }
    }
}
