package me.goldentrio.source.standard

import me.goldentrio.source.Source
import me.goldentrio.source.Sources
import me.goldentrio.util.differ.Difference
import me.goldentrio.util.extention.path
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

        if (config.expected != null) {
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

class FileConfiguration {

    var name: String? = null

    var valueSplit = " "

    var lineJoin = "\n"
    var valueJoin = " "

    var outExtension = "out"

    var expected: File? = null

    fun expected(path: Path) {
        expected = path.toFile()
    }

    fun expected(path: String) = expected(path.path)

}

fun Sources.file(file: File, config: FileConfiguration.() -> Unit = {}) {
    +FileSource(file, FileConfiguration().apply(config))
}

fun Sources.file(path: Path, config: FileConfiguration.() -> Unit = {}) = file(path.toFile(), config)

fun Sources.file(path: String, config: FileConfiguration.() -> Unit = {}) = file(path.path, config)