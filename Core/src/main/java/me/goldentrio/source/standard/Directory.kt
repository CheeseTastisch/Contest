package me.goldentrio.source.standard

import me.goldentrio.source.Sources
import me.goldentrio.util.extention.path
import java.io.File
import java.nio.file.Path

class DirectoryConfiguration {

    var valueSplit = " "

    var lineJoin = "\n"
    var valueJoin = " "

    var outExtension = "out"

    internal val expectations = mutableMapOf<String, File>()

    fun expected(file: String, expected: File) {
        expectations[file] = expected
    }

    fun expected(file: String, expected: Path) = expected(file, expected.toFile())

    fun expected(file: String, expected: String) = expected(file, expected.path)

}

fun Sources.directory(directory: File, config: DirectoryConfiguration.() -> Unit = {}) {
    val configuration = DirectoryConfiguration().apply(config)

    directory.listFiles()
        ?.filter { it.isFile }
        ?.filter { it.extension != configuration.outExtension }
        ?.forEach {
            +FileSource(it, FileConfiguration().apply {
                valueSplit = configuration.valueSplit

                lineJoin = configuration.lineJoin
                valueJoin = configuration.valueJoin

                outExtension = configuration.outExtension

                expected = configuration.expectations[it.name]
                    ?: configuration.expectations[it.nameWithoutExtension]
            })
        }
}

fun Sources.directory(directory: Path, config: DirectoryConfiguration.() -> Unit = {}) = directory(directory.toFile(), config)

fun Sources.directory(directory: String, config: DirectoryConfiguration.() -> Unit = {}) = directory(directory.path, config)