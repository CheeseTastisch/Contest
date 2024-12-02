package me.task.source.standard

import me.task.source.Sources
import java.io.File


/**
 * The configuration for a single location aware file source.
 */
class SingleLocationConfiguration {

    /**
     * The name of the task.
     */
    var name: String? = null

    /**
     * The [String] used to split values of a line.
     */
    var valueSplit = " "

    /**
     * The [String] used to join to values when writing the output.
     */
    var valueJoin = " "

    /**
     * The [String] used to join lines when writing the output.
     */
    var lineJoin = "\n"

    /**
     * The input file extension
     */
    var inExtension = "in"

    /**
     * The extension of the output file.
     */
    var outExtension = "out"

    /**
     * The expected output file extension (compares the content of the output file with this file).
     */
    var expected: String? = null

}

fun Sources.location(config: DirectoryConfiguration.() -> Unit = {}) {
    val caller = Thread.currentThread().stackTrace[2]
    val callerFile = File(caller.fileName ?: throw IllegalStateException("No file name found in stack trace"))

    var moduleDirectory = callerFile
    while (!moduleDirectory.isModuleDirectory()) {
        moduleDirectory = moduleDirectory.parentFile
    }

    directory(File(moduleDirectory, "in/${callerFile.nameWithoutExtension}"), config)
}

fun Sources.singeLocation(config: SingleLocationConfiguration.() -> Unit = {}) {
    val configuration = SingleLocationConfiguration().apply(config)

    val caller = Thread.currentThread().stackTrace[2]
    val callerFile = File(caller.fileName ?: throw IllegalStateException("No file name found in stack trace"))

    var moduleDirectory = callerFile
    while (!moduleDirectory.isModuleDirectory()) {
        moduleDirectory = moduleDirectory.parentFile
    }

    file(File(moduleDirectory, "in/${callerFile.nameWithoutExtension}/${caller.methodName}.${configuration.inExtension}")) {
        name = configuration.name ?: caller.methodName

        valueSplit = configuration.valueSplit
        valueJoin = configuration.valueJoin
        lineJoin = configuration.lineJoin
        outExtension = configuration.outExtension

        if (configuration.expected != null)
            expected = File(moduleDirectory, "in/${callerFile.nameWithoutExtension}.${configuration.expected}")
    }
}

private fun File.isModuleDirectory(): Boolean =
    isDirectory &&
            listFiles()?.any { it.name == "src" } == true &&
            listFiles()?.any { it.name == "build.gradle.kts" } == true