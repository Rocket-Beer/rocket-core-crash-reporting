package com.rocket.android.core.crashreporting.file.printer

import android.app.Application
import android.content.Context
import android.os.Environment
import com.rocket.core.crashreporting.logger.LogLevel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

@ExperimentalCoroutinesApi
internal class FileLogPrinterTest {
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @TempDir
    lateinit var file: File

    @MockK
    lateinit var application: Application

    @MockK
    lateinit var context: Context

    private lateinit var fileLogPrinter: FileLogPrinter

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)

        mockkStatic(Environment::class) {
            every { Environment.getExternalStoragePublicDirectory(any()) } returns file
        }
        every { application.applicationContext } returns context
        every { context.packageName } returns "rocket.crash.test.package"

        fileLogPrinter = spyk(FileLogPrinter(application, testDispatcher))
    }

    @Test
    fun `defaultLogPrinter with DEBUG logs debug`() = runBlockingTest {
        val tag = "tag"
        val msg = "message"
        val logLevel = LogLevel.DEBUG
        val message = "$logLevel/$tag: $msg"

        fileLogPrinter.printMessage(tag, msg, logLevel)

        verify(exactly = 1) {
            fileLogPrinter["writeToFile"](message)
        }
    }

    @Test
    fun `defaultLogPrinter with INFO logs info`() {
        val tag = "tag"
        val msg = "message"
        val logLevel = LogLevel.INFO
        val message = "$logLevel/$tag: $msg"

        fileLogPrinter.printMessage(tag, msg, logLevel)

        verify(exactly = 1) {
            fileLogPrinter["writeToFile"](message)
        }
    }

    @Test
    fun `defaultLogPrinter with WARN logs warn`() {
        val tag = "tag"
        val msg = "message"
        val logLevel = LogLevel.WARN
        val message = "$logLevel/$tag: $msg"

        fileLogPrinter.printMessage(tag, msg, logLevel)

        verify(exactly = 1) {
            fileLogPrinter["writeToFile"](message)
        }
    }

    @Test
    fun `defaultLogPrinter with ERROR logs debug`() {
        val tag = "tag"
        val msg = "message"
        val logLevel = LogLevel.ERROR
        val message = "$logLevel/$tag: $msg"

        fileLogPrinter.printMessage(tag, msg, logLevel)

        verify(exactly = 1) {
            fileLogPrinter["writeToFile"](message)
        }
    }
}
