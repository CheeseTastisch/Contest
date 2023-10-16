package me.goldentrio.source

import java.util.Deque

/**
 * A single [Source] is an input for a task and
 * the declaration of what should happen with the output of a task.
 */
interface Source {

    /**
     * The input for a task.
     */
    val input: Deque<Deque<String>>

    /**
     * Writes the output of a task.
     */
    fun writeOutput(output: Deque<Deque<String>>)

}