package com.rocket.core.crashreporting.logger

import com.rocket.core.crashreporting.printer.DefaultLogPrinter
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class DefaultLoggerTest {
    @MockK
    lateinit var defaultLogPrinter: DefaultLogPrinter

    private lateinit var defaultCrashLogger: DefaultLogger

    @BeforeEach
    fun setup() {
        defaultLogPrinter = spyk(DefaultLogPrinter())
    }

    @ParameterizedTest(name = "crashlogger with debuggable {0} log messages {1}")
    @MethodSource("crashloggerDebuggableArgs")
    fun `crashlogger log messages`(debuggable: Boolean, expectedResult: Boolean) {
        defaultCrashLogger = DefaultLogger(debuggable = debuggable, defaultLogPrinter)

        defaultCrashLogger.log(message = "message")

        val calls = if (expectedResult) 1 else 0
        verify(exactly = calls) {
            defaultLogPrinter.printMessage(any(), any(), any())
        }
    }

    @ParameterizedTest(name = "crashlogger with debuggable {0} log exceptions {1}")
    @MethodSource("crashloggerDebuggableArgs")
    fun `crashlogger log exceptions`(debuggable: Boolean, expectedResult: Boolean) {
        defaultCrashLogger = DefaultLogger(debuggable = debuggable, defaultLogPrinter)

        defaultCrashLogger.log(exception = Exception("exception message"))

        val calls = if (expectedResult) 1 else 0
        verify(exactly = calls) {
            defaultLogPrinter.printMessage(any(), any(), any())
        }
    }

    companion object {
        @JvmStatic
        fun crashloggerDebuggableArgs(): Stream<Arguments> =
            Stream.of(
                Arguments.of(true, true),
                Arguments.of(false, false)
            )
    }
}
