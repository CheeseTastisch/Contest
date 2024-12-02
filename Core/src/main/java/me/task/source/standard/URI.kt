package me.task.source.standard

import me.task.source.Source
import me.task.source.Sources
import me.task.util.differ.Difference
import org.apache.commons.io.IOUtils
import java.net.URI
import java.util.*

internal class URISource(
    private val uri: URI,
    private val config: URIConfig,
) : Source {

    private val stringInput by lazy {
        IOUtils.toString(uri, Charsets.UTF_8)
    }

    override val input: Deque<Deque<String>> =
        stringInput.split(config.lineSplit)
            .map { it.split(config.valueSplit).toCollection(LinkedList()) }
            .toCollection(LinkedList())

    override fun writeOutput(output: Deque<Deque<String>>) {
        val outputString = output
            .joinToString(config.lineJoin) { it.joinToString(config.valueJoin).trim() }
            .trim()

        println(
            """
            ------------ Task ${config.name ?: "unnamed"} ------------
            > Input
            $uri
            - same as -
            $stringInput
            
            
            > Output
            $outputString
            """.trimIndent()
        )

        if (config.expected != null) {
            println(
                """
                
                > Expected
                ${config.expected}
                
                > Difference
                ${Difference.getDifference(outputString, config.expected!!)}
                """.trimIndent()
            )
        }
        println("------------------------------------------------------")
    }

}

class URIConfig {

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
 * Adds a [URISource] to the [Sources].
 */
fun Sources.uri(uri: URI, config: URIConfig.() -> Unit = {}) {
    +URISource(uri, URIConfig().apply(config))
}

/**
 * Adds a [URISource] to the [Sources].
 */
fun Sources.uri(uri: String, config: URIConfig.() -> Unit = {}) = uri(URI(uri), config)