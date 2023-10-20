package me.goldentrio.io

import java.util.*

/**
 * The [IO] interface is used to interact with an input and the output of a specific task in a contest.
 */
interface IO {

    /**
     * The [inputQueue] is a queue of lines that are read from the input.
     */
    val inputQueue: Deque<Deque<String>>

    /**
     * The [outputQueue] is a queue of lines that are written to the output.
     */
    val outputQueue: Deque<Deque<String>>

    /**
     * Reads the next line from the input.
     *
     * If [peek] is true, the line will not be popped from the input.
     */
    fun readWholeLine(peek: Boolean = false): String

    /**
     * Reads the next line from the input.
     *
     * If [peek] is true, the line will not be popped from the input.
     */
    fun readLine(peek: Boolean = false): List<String>

    /**
     * Reads the next value from the input.
     *
     * If [peek] is true, the value will not be popped from the input.
     *
     * IF [endOfLine] is true, the rest of the line will be removed from the input.
     */
    fun readValue(peek: Boolean = false, endOfLine: Boolean = false): String

    /**
     * Reads the next int from the input.
     *
     * If [peek] is true, the int will not be popped from the input.
     *
     * IF [endOfLine] is true, the rest of the line will be removed from the input.
     */
    fun readInt(peek: Boolean = false, endOfLine: Boolean = false): Int

    /**
     * Reads the next ints from the input (the rest of the line).
     *
     * If [peek] is true, the ints will not be popped from the input.
     */
    fun readInts(peek: Boolean = false): List<Int>

    /**
     * Reads the next double from the input.
     *
     * If [peek] is true, the double will not be popped from the input.
     *
     * IF [endOfLine] is true, the rest of the line will be removed from the input.
     */
    fun readDouble(peek: Boolean = false, endOfLine: Boolean = false): Double

    /**
     * Reads the next doubles from the input (the rest of the line).
     *
     * If [peek] is true, the doubles will not be popped from the input.
     */
    fun readDoubles(peek: Boolean = false): List<Double>

    /**
     * Checks if the input has a next line.
     */
    fun hasNextLine(): Boolean

    /**
     * Checks if the input has a next value.
     */
    fun hasNextValue(): Boolean

    /**
     * Gets or creates the current output line.
     */
    fun getCurrentLine(): Deque<String>

    /**
     * Writes the given [line] to the output.
     *
     * This will break the current line and start a new one.
     * If [lineBreak] is true, a line break will be added to the end of the line.
     */
    fun writeLine(line: String, lineBreak: Boolean = false)

    /**
     * Writes the given [value] to the output.
     *
     * If [lineBreak] is true, a line break will be added to the end of the value.
     */
    fun writeValue(value: String, lineBreak: Boolean = false)

    /**
     * Writes the given [Int] to the output.
     *
     * If [lineBreak] is true, a line break will be added to the end of the value.
     */
    fun writeInt(value: Int, lineBreak: Boolean = false)

    /**
     * Writes the given [Int]s to the output.
     *
     * If [lineBreak] is true, a line break will be added to the end of the value.
     */
    fun writeInts(vararg values: Int, lineBreak: Boolean = false)

    /**
     * Writes the given [Double] to the output.
     *
     * If [lineBreak] is true, a line break will be added to the end of the value.
     */
    fun writeDouble(value: Double, lineBreak: Boolean = false)

    /**
     * Writes the given [Double]s to the output.
     *
     * If [lineBreak] is true, a line break will be added to the end of the value.
     */
    fun writeDoubles(vararg values: Double, lineBreak: Boolean = false)

    /**
     * Stops the current output line and starts a new one.
     */
    fun writeBreak()

    /**
     * Calls the solve function again for the rest of the input.
     *
     * If no input is left, the solve function will not be called again.
     */
    fun repeat()

}