package me.task.source.standard

import me.task.source.Source
import me.task.source.Sources
import me.task.util.differ.Difference
import me.task.util.extention.path
import java.io.File
import java.nio.file.Path
import java.util.*

internal class FileSource(
    private val inputFile: File,
    private val config: FileConfiguration,
) : Source {

    init {
        if (config.name == null) config.name = inputFile.nameWithoutExtension
    }

    override val input: Deque<Deque<String>> =
        inputFile.readLines()
            .map { it.split(config.valueSplit).toCollection(LinkedList()) }
            .toCollection(LinkedList())

    override fun writeOutput(output: Deque<Deque<String>>) {
        val outputString = output
            .joinToString(config.lineJoin) { it.joinToString(config.valueJoin).trim() }
            .trim()

        val outputFile = File(inputFile.parentFile, "${inputFile.name}.${config.outExtension}")
        if (outputFile.exists()) outputFile.delete()

        outputFile.createNewFile()
        outputFile.writeText(outputString)

        val expected = config.expected
            ?: config.expectedExtension?.let { File(inputFile.parentFile, "${inputFile.nameWithoutExtension}.$it") }

        if (expected?.exists() == true) {
            println(
                """
                ------------ Task ${config.name ?: "unnamed"} ------------
                > Input: ${inputFile.absolutePath}
            """.trimIndent()
            )
            println()
            println(inputFile.readText())

            println(
                """
                
                > Output: ${outputFile.absolutePath}
            """.trimIndent()
            )
            println(outputString)

            println(
                """
                
                > Expected: ${config.expected!!.absolutePath}
            """.trimIndent()
            )
            println(config.expected!!.readLines().joinToString("\n"))

            println(
                """
                
                > Difference
            """.trimIndent()
            )
            println(Difference.getDifference(outputString, config.expected!!))
            println("------------------------------------------------------")
        }
    }
}

/**
 * The configuration for a [FileSource].
 */
class FileConfiguration {

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
     * The extension of the output file.
     */
    var outExtension = "out"

    /**
     * The expected output file (compares the content of the output file with this file).
     */
    var expected: File? = null

    /**
     * The extension of the expected file (appended to the input file name).
     * This is only used if [expected] is not set.
     */
    var expectedExtension: String? = null

    /**
     * Sets the expected output file.
     */
    fun expected(path: Path) {
        expected = path.toFile()
    }

    /**
     * Sets the expected output file.
     */
    fun expected(path: String) = expected(path.path)

}

/**
 * Adds a [FileSource] to the [Sources].
 */
fun Sources.file(file: File, config: FileConfiguration.() -> Unit = {}) {
    +FileSource(file, FileConfiguration().apply(config))
}

/**
 * Adds a [FileSource] to the [Sources].
 */
fun Sources.file(path: Path, config: FileConfiguration.() -> Unit = {}) = file(path.toFile(), config)

/**
 * Adds a [FileSource] to the [Sources].
 */
fun Sources.file(path: String, config: FileConfiguration.() -> Unit = {}) = file(path.path, config)

/**
 * Adds a [FileSource] to the [Sources], where file is the `{currentModule}/in/[path]`.
 */
fun Sources.inferFile(path: String, config: FileConfiguration.() -> Unit = {}) {
    val file = getModuleDirectory().resolve("in").resolve(path)
    file(file, config)
}