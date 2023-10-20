package me.goldentrio.util.differ

import me.goldentrio.util.extention.random
import java.io.File

internal object Differnece {

    @Suppress("MemberVisibilityCanBePrivate")
    fun getDifference(output: File, expected: File): String {
        while (output.readText().endsWith("\n")) output.writeText(output.readText().dropLast(1))
        while (expected.readText().endsWith("\n")) expected.writeText(expected.readText().dropLast(1))

        val process = ProcessBuilder(
            "git",
            "diff",
            "--color=always",
            "--no-index",
            expected.absolutePath,
            output.absolutePath
        ).start()
            .onExit()
            .join()

        return if (process.exitValue() == 0) "No difference"
        else if (process.exitValue() == 1) {
            process.inputStream.bufferedReader()
                .readText()
                .lines()
                .toMutableList()
                .also { for (i in 0..<4) it.removeFirst() }
                .joinToString("\n")
        } else throw IllegalStateException("Git diff failed with exit code ${process.exitValue()}")
    }

    fun getDifference(output: File, expected: String): String {
        val expectedFile = File.createTempFile("expected-${String.random()}", ".tmp")
        expectedFile.writeText(expected)

        return getDifference(output, expectedFile)
    }

    fun getDifference(output: String, expected: File): String {
        val outputFile = File.createTempFile("output-${String.random()}", ".tmp")
        outputFile.writeText(output)

        return getDifference(outputFile, expected)
    }

    fun getDifference(output: String, expected: String): String {
        val outputFile = File.createTempFile("output-${String.random()}", ".tmp")
        outputFile.writeText(output)

        val expectedFile = File.createTempFile("expected-${String.random()}", ".tmp")
        expectedFile.writeText(expected)

        return getDifference(outputFile, expectedFile)
    }

}