package me.goldentrio.source.standard

import me.goldentrio.source.Source
import me.goldentrio.source.Sources
import me.goldentrio.util.differ.Differnece
import java.io.File
import java.nio.file.Path
import java.util.*

internal class FileSource(
    private val inputFile: File,
    private val config: FileSourceConfiguration,
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
            println("""
                ------------ Task ${config.name ?: "unnamed"} ------------
                > Input: ${inputFile.absolutePath}
            """.trimIndent())
            println()
            println(inputFile.readText())

            println("""
                
                > Output: ${outputFile.absolutePath}
            """.trimIndent())
            println(outputString)

            println("""
                
                > Expected: ${config.expected!!.absolutePath}
            """.trimIndent())
            println(config.expected!!.readLines().joinToString("\n"))

            println("""
                
                > Difference
            """.trimIndent())
            println(Differnece.getDifference(outputString, config.expected!!))
            println("------------------------------------------------------")
        }
    }
}

class FileSourceConfiguration {

    var name: String? = null

    var valueSplit = " "

    var lineJoin = "\n"
    var valueJoin = " "

    var outExtension = "out"

    var expected: File? = null

    var expectedPath: Path?
        get() = expected?.toPath()
        set(value) {
            expected = value?.toFile()
        }

}

fun Sources.file(file: File, config: FileSourceConfiguration.() -> Unit = {}) {
    +FileSource(file, FileSourceConfiguration().apply(config))
}

fun Sources.file(path: Path, config: FileSourceConfiguration.() -> Unit = {}) {
    +FileSource(path.toFile(), FileSourceConfiguration().apply(config))
}

fun Sources.files(directory: File, config: FileSourceConfiguration.() -> Unit = {}) {
    val configuration = FileSourceConfiguration().apply(config)

    directory.listFiles()
        ?.filter { it.isFile }
        ?.filter { it.extension != configuration.outExtension }
        ?.forEach {
            +FileSource(it, configuration)
    }
}

fun Sources.files(directory: Path, config: FileSourceConfiguration.() -> Unit = {}) {
    files(directory.toFile(), config)
}