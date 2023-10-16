package me.goldentrio.source

/**
 * A list of [Sources] used to read the inputs for a task and
 * write the outputs for a task.
 */
interface Sources {

    /**
     * The [List] of [Source]s used to read the inputs for a task and
     */
    val sources: List<Source>

    /**
     * Adds the given [Source] to the [List] of [Source]s.
     */
    operator fun Source.unaryPlus()

}