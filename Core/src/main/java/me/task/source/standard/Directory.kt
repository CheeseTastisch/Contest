package me.task.source.standard

import me.task.source.Sources
import me.task.util.extention.path
import java.io.File
import java.nio.file.Path

/**
 * A [Sources] configuration for a directory.
 */
class DirectoryConfiguration {

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
     * The extension of the output file.
     */
    var outExtension = "out"

    internal val expectations = mutableMapOf<String, File>()

    /**
     * Sets the expected output file for a specific input file, with the given name.
     */
    fun expected(file: String, expected: File) {
        expectations[file] = expected
    }

    /**
     * Sets the expected output file for a specific input file, with the given name.
     */
    fun expected(file: String, expected: Path) = expected(file, expected.toFile())

    /**
     * Sets the expected output file for a specific input file, with the given name.
     */
    fun expected(file: String, expected: String) = expected(file, expected.path)

}

/**
 * Adds all files from a directory to the [Sources].
 */
fun Sources.directory(directory: File, config: DirectoryConfiguration.() -> Unit = {}) {
    val configuration = DirectoryConfiguration().apply(config)

    directory.listFiles()
        ?.filter { it.isFile }
        ?.filter { it.extension != configuration.outExtension }
        ?.sortedBy { it.name }
        ?.forEach { file ->
            +FileSource(file, FileConfiguration().apply {
                valueSplit = configuration.valueSplit

                lineJoin = configuration.lineJoin
                valueJoin = configuration.valueJoin

                outExtension = configuration.outExtension

                expected = (configuration.expectations[file.name]
                    ?: configuration.expectations[file.nameWithoutExtension])
                    ?.let { directory.resolve(it) }
            })
        }
}

/**
 * Adds all files from a directory to the [Sources].
 */
fun Sources.directory(directory: Path, config: DirectoryConfiguration.() -> Unit = {}) =
    directory(directory.toFile(), config)

/**
 * Adds all files from a directory to the [Sources].
 */
fun Sources.directory(directory: String, config: DirectoryConfiguration.() -> Unit = {}) =
    directory(directory.path, config)

/**
 * Adds all files from a directory to the [Sources], which is a subdirectory of `{currentModule}/in/[directory]`
 */
fun Sources.inferDirectory(name: String, config: DirectoryConfiguration.() -> Unit = {}) {
    val directory = getModuleDirectory().resolve("in").resolve(name)
    directory(directory, config)
}

internal fun getModuleDirectory(): File {
    val caller = Thread.currentThread().stackTrace[3]
    val callerFile = File(caller.fileName ?: throw IllegalStateException("No file name found in stack trace"))

    var moduleDirectory = callerFile
    while (!moduleDirectory.isModuleDirectory()) {
        moduleDirectory = moduleDirectory.parentFile
    }

    return moduleDirectory
}

internal fun File.isModuleDirectory(): Boolean =
    isDirectory &&
            listFiles()?.any { it.name == "src" } == true &&
            listFiles()?.any { it.name == "build.gradle.kts" } == true