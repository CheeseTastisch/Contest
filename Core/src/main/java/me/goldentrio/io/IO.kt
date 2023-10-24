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
     * If [peek] is set, the line will be kept in the [inputQueue].
     */
    @Deprecated(
        "Use readValues with join instead",
        ReplaceWith("readValues(peek).joinToString(separator)"),
        level = DeprecationLevel.WARNING
    )
    fun readWholeLine(peek: Boolean = false): String

    /**
     * Reads the next line from the input
     *
     * If [peek] is set, the line will be kept in the [inputQueue].
     */
    @Deprecated(
        "Use readValues instead",
        ReplaceWith("readValues(peek)"),
        level = DeprecationLevel.WARNING
    )
    fun readLine(peek: Boolean = false): List<String>

    /**
     * Reads the next value from the input.
     *
     * If [peek] is set, the value will be kept in the [inputQueue].
     * If [endOfLine] is set, the whole line will be removed from the [inputQueue].
     *
     * @throws IllegalArgumentException if [peek] and [endOfLine] are both set.
     */
    @Throws(IllegalArgumentException::class)
    fun readValue(peek: Boolean = false, endOfLine: Boolean = false): String

    /**
     * Reads the next value from the input and parses it to an [Byte].
     *
     * If [peek] is set, the value will be kept in the [inputQueue].
     * If [endOfLine] is set, the whole line will be removed from the [inputQueue].
     *
     * @throws IllegalArgumentException if [peek] and [endOfLine] are both set.
     */
    @Throws(IllegalArgumentException::class)
    fun readByte(peek: Boolean = false, endOfLine: Boolean = false) = readValue(peek, endOfLine).toByte()

    /**
     * Reads the next value from the input and parses it to an [Short].
     *
     * If [peek] is set, the value will be kept in the [inputQueue].
     * If [endOfLine] is set, the whole line will be removed from the [inputQueue].
     *
     * @throws IllegalArgumentException if [peek] and [endOfLine] are both set.
     */
    @Throws(IllegalArgumentException::class)
    fun readShort(peek: Boolean = false, endOfLine: Boolean = false) = readValue(peek, endOfLine).toShort()

    /**
     * Reads the next value from the input and parses it to an [Int].
     *
     * If [peek] is set, the value will be kept in the [inputQueue].
     * If [endOfLine] is set, the whole line will be removed from the [inputQueue].
     *
     * @throws IllegalArgumentException if [peek] and [endOfLine] are both set.
     */
    @Throws(IllegalArgumentException::class)
    fun readInt(peek: Boolean = false, endOfLine: Boolean = false) = readValue(peek, endOfLine).toInt()

    /**
     * Reads the next value from the input and parses it to an [Long].
     *
     * If [peek] is set, the value will be kept in the [inputQueue].
     * If [endOfLine] is set, the whole line will be removed from the [inputQueue].
     *
     * @throws IllegalArgumentException if [peek] and [endOfLine] are both set.
     */
    @Throws(IllegalArgumentException::class)
    fun readLong(peek: Boolean = false, endOfLine: Boolean = false) = readValue(peek, endOfLine).toLong()

    /**
     * Reads the next value from the input and parses it to an [Float].
     *
     * If [peek] is set, the value will be kept in the [inputQueue].
     * If [endOfLine] is set, the whole line will be removed from the [inputQueue].
     *
     * @throws IllegalArgumentException if [peek] and [endOfLine] are both set.
     */
    @Throws(IllegalArgumentException::class)
    fun readFloat(peek: Boolean = false, endOfLine: Boolean = false) = readValue(peek, endOfLine).toFloat()

    /**
     * Reads the next value from the input and parses it to an [Double].
     *
     * If [peek] is set, the value will be kept in the [inputQueue].
     * If [endOfLine] is set, the whole line will be removed from the [inputQueue].
     *
     * @throws IllegalArgumentException if [peek] and [endOfLine] are both set.
     */
    @Throws(IllegalArgumentException::class)
    fun readDouble(peek: Boolean = false, endOfLine: Boolean = false) = readValue(peek, endOfLine).toDouble()

    /**
     * Reads the next line from the input.
     *
     * If [peek] is set, the line will be kept in the [inputQueue].
     */
    fun readValues(peek: Boolean = false): List<String>

    /**
     * Reads the next line from the input and parses its values to [Byte]s.
     *
     * If [peek] is set, the line will be kept in the [inputQueue].
     */
    fun readBytes(peek: Boolean = false) = readValues(peek).map { it.toByte() }

    /**
     * Reads the next line from the input and parses its values to [Short]s.
     *
     * If [peek] is set, the line will be kept in the [inputQueue].
     */
    fun readShorts(peek: Boolean = false) = readValues(peek).map { it.toShort() }

    /**
     * Reads the next line from the input and parses its values to [Int]s.
     *
     * If [peek] is set, the line will be kept in the [inputQueue].
     */
    fun readInts(peek: Boolean = false) = readValues(peek).map { it.toInt() }

    /**
     * Reads the next line from the input and parses its values to [Long]s.
     *
     * If [peek] is set, the line will be kept in the [inputQueue].
     */
    fun readLongs(peek: Boolean = false) = readValues(peek).map { it.toLong() }

    /**
     * Reads the next line from the input and parses its values to [Float]s.
     *
     * If [peek] is set, the line will be kept in the [inputQueue].
     */
    fun readFloats(peek: Boolean = false) = readValues(peek).map { it.toFloat() }

    /**
     * Reads the next line from the input and parses its values to [Double]s.
     *
     * If [peek] is set, the line will be kept in the [inputQueue].
     */
    fun readDoubles(peek: Boolean = false) = readValues(peek).map { it.toDouble() }

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
     * If [append] is set, the line will be appended to the current output line.
     * If [lineBreak] is set, this will be the last value of the line.
     */
    @Deprecated(
        "Use writeLine with vararg instead",
        ReplaceWith("writeValue(*line.split(\" \").toTypedArray(), append = append, lineBreak = lineBreak)"),
        level = DeprecationLevel.WARNING
    )
    fun writeLine(
        line: String,
        append: Boolean = false,
        lineBreak: Boolean = false
    )

    /**
     * Writes the given [values] to the output.
     * This will map all values to [String]s, by calling there `toString` function.
     *
     * If [append] is set, the value will be appended to the current output line.
     * If [lineBreak] is set, this will be the last value of the line.
     */
    fun writeValue(
        vararg values: Any = emptyArray(),
        append: Boolean = true,
        lineBreak: Boolean = false,
    )

    /**
     * Writes the given [Int] to the output.
     *
     * If [lineBreak] is true, a line break will be added to the end of the value.
     */
    @Deprecated(
        "Use writeValue instead",
        ReplaceWith("writeValue(value, lineBreak = lineBreak)"),
        level = DeprecationLevel.WARNING
    )
    fun writeInt(value: Int, lineBreak: Boolean = false)

    /**
     * Writes the given [Int]s to the output.
     *
     * If [lineBreak] is true, a line break will be added to the end of the value.
     */
    @Deprecated(
        "Use writeValue instead",
        ReplaceWith("writeValue(*values.toTypedArray(), lineBreak = lineBreak)"),
        level = DeprecationLevel.WARNING
    )
    fun writeInts(vararg values: Int, lineBreak: Boolean = false)

    /**
     * Writes the given [Double] to the output.
     *
     * If [lineBreak] is true, a line break will be added to the end of the value.
     */
    @Deprecated(
        "Use writeValue instead",
        ReplaceWith("writeValue(value, lineBreak = lineBreak)"),
        level = DeprecationLevel.WARNING
    )
    fun writeDouble(value: Double, lineBreak: Boolean = false)

    /**
     * Writes the given [Double]s to the output.
     *
     * If [lineBreak] is true, a line break will be added to the end of the value.
     */
    @Deprecated(
        "Use writeValue instead",
        ReplaceWith("writeValue(*values.toTypedArray(), lineBreak = lineBreak)"),
        level = DeprecationLevel.WARNING
    )
    fun writeDoubles(vararg values: Double, lineBreak: Boolean = false)

    /**
     * Stops the current output line and starts a new one.
     */
    @Deprecated(
        "Use writeValue instead",
        ReplaceWith("writeValue(lineBreak = true)"),
        level = DeprecationLevel.WARNING
    )
    fun writeBreak()

    /**
     * Calls the solve function again for the rest of the input.
     *
     * If [hasNextLine] is `false`, the solve function will not be called again.
     */
    fun repeat()

}