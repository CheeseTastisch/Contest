package me.goldentrio.source.standard

import me.goldentrio.source.Source
import me.goldentrio.source.Sources
import me.goldentrio.util.differ.Differnece
import java.util.*

internal class StringSource(
    private val inputString: String,
    private val config: StringSourceConfiguration,
) : Source {

    override val input: Deque<Deque<String>> =
        inputString.split(config.lineSplit)
            .map { it.split(config.valueSplit).toCollection(LinkedList()) }
            .toCollection(LinkedList())

    override fun writeOutput(output: Deque<Deque<String>>) {
        val outputString = output
            .joinToString(config.lineJoin) { it.joinToString(config.valueJoin).trim() }
            .trim()

        println("""
            ------------ Task ${config.name ?: "unnamed"} ------------
            > Input
        """.trimIndent())
        println(inputString)
        println("""
                
                > Output
            """.trimMargin())
        println(outputString)

        if (config.expected != null) {
            println("""
                
                > Expected
            """.trimIndent())
            println(config.expected)

            println("""
                
                > Difference
            """.trimMargin())
            println(Differnece.getDifference(outputString, config.expected!!))
        }
        println("------------------------------------------------------")
    }

}

class StringSourceConfiguration {

    var name: String? = null

    var lineSplit = "\n"
    var valueSplit = " "

    var lineJoin = "\n"
    var valueJoin = " "

    var expected: String? = null

}

fun Sources.string(string: String, config: StringSourceConfiguration.() -> Unit = {}) {
    +StringSource(string, StringSourceConfiguration().apply(config))
}