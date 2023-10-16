package me.goldentrio.implementation

import java.io.File
import me.goldentrio.Configuration as ConfigurationInterface

internal class Configuration : ConfigurationInterface {

    private val _inputs = mutableMapOf<String, (String) -> Unit>()

    val input
        get() = _inputs.toList()

    override fun String.unaryPlus() {
        _inputs[this] = { println(it) }
    }

    override fun File.unaryPlus() {
        if (this.isDirectory) {
            this.listFiles()
                ?.filter { it.isFile }
                ?.forEach { +it }
        } else if (this.isFile && this.extension != "out") {
            _inputs[this.readText()] = { this.parentFile.resolve(this.nameWithoutExtension + ".out").writeText(it) }
        }
    }
}