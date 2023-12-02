package me.task.source.standard

import me.task.source.Source
import me.task.source.Sources
import me.task.util.differ.Difference
import java.util.*

internal class StringSource(
    private val inputString: String,
    private val config: StringConfig,
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
            """.trimIndent())
        println(outputString)

        if (config.expected != null) {
            println("""
                
                > Expected
            """.trimIndent())
            println(config.expected)

            println("""
                
                > Difference
            """.trimIndent())
            println(Difference.getDifference(outputString, config.expected!!))
        }
        println("------------------------------------------------------")
    }

}

class StringConfig {

    /**
     * The name of the task.
     */
    var name: String? = null

    /**
     * The [String] used to split lines.
     */
    var lineSplit = "\n"

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
     * The expected output.
     */
    var expected: String? = null

}

/**
 * Adds a [StringSource] to the [Sources].
 */
fun Sources.string(string: String, config: StringConfig.() -> Unit = {}) {
    +StringSource(string, StringConfig().apply(config))
}