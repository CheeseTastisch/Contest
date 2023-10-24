package me.goldentrio.io

import me.goldentrio.source.Source
import java.util.*

internal class IOImplementation(
    source: Source,
    val solve: IO.() -> Unit,
) : IO {

    override val inputQueue: Deque<Deque<String>> = source.input
    override val outputQueue: Deque<Deque<String>> = LinkedList()

    init {
        filterEmptyInput()
        this.solve()
        filterEmptyOutput()
        source.writeOutput(outputQueue)
    }

    @Deprecated(
        "Use readValues with join instead",
        replaceWith = ReplaceWith("readValues(peek).joinToString(separator)"),
        level = DeprecationLevel.WARNING
    )
    override fun readWholeLine(peek: Boolean) = readValues(peek).joinToString(" ")

    @Deprecated(
        "Use readValues instead",
        replaceWith = ReplaceWith("readValues(peek)"),
        level = DeprecationLevel.WARNING
    )
    override fun readLine(peek: Boolean) = readValues(peek)

    override fun readValue(peek: Boolean, endOfLine: Boolean): String {
        if (peek && endOfLine) throw IllegalArgumentException("peek and endOfLine can't be both set")

        return if (endOfLine) inputQueue.pop().pop()
        else if (peek) inputQueue.peek().peek()
        else inputQueue.peek().pop()
    }

    override fun readValues(peek: Boolean) =
        if (peek) inputQueue.peek().toList()
        else inputQueue.pop().toList()

    override fun hasNextLine() = inputQueue.isNotEmpty()

    override fun hasNextValue() = inputQueue.peek().isNotEmpty()

    override fun getCurrentLine(): Deque<String> {
        if (outputQueue.isEmpty()) outputQueue.add(LinkedList())
        return outputQueue.peekLast()
    }

    @Deprecated(
        "Use writeLine with vararg instead",
        replaceWith = ReplaceWith(
            "writeValue(*line.split(\" \").toTypedArray(), append = append, lineBreak = lineBreak)"
        ),
        level = DeprecationLevel.WARNING
    )
    override fun writeLine(line: String, append: Boolean, lineBreak: Boolean) =
        writeValue(*line.split(" ").toTypedArray(), append = append, lineBreak = lineBreak)

    override fun writeValue(vararg values: Any, append: Boolean, lineBreak: Boolean) {
        if (!append && outputQueue.peekLast().isNotEmpty()) outputQueue.add(LinkedList())

        values.forEach { getCurrentLine().add(it.toString()) }

        if (lineBreak) outputQueue.add(LinkedList())
    }

    @Deprecated(
        "Use writeValue instead",
        replaceWith = ReplaceWith("writeValue(value, lineBreak = lineBreak)"),
        level = DeprecationLevel.WARNING
    )
    override fun writeInt(value: Int, lineBreak: Boolean) = writeValue(value, lineBreak = lineBreak)

    @Deprecated(
        "Use writeValue instead",
        replaceWith = ReplaceWith("writeValue(*values.toTypedArray(), lineBreak = lineBreak)"),
        level = DeprecationLevel.WARNING
    )
    override fun writeInts(vararg values: Int, lineBreak: Boolean) = writeValue(*values.toTypedArray(), lineBreak = lineBreak)

    @Deprecated(
        "Use writeValue instead",
        replaceWith = ReplaceWith("writeValue(value, lineBreak = lineBreak)"),
        level = DeprecationLevel.WARNING
    )
    override fun writeDouble(value: Double, lineBreak: Boolean) = writeValue(value, lineBreak = lineBreak)

    @Deprecated(
        "Use writeValue instead",
        replaceWith = ReplaceWith("writeValue(*values.toTypedArray(), lineBreak = lineBreak)"),
        level = DeprecationLevel.WARNING
    )
    override fun writeDoubles(vararg values: Double, lineBreak: Boolean) = writeValue(*values.toTypedArray(), lineBreak = lineBreak)

    @Deprecated(
        "Use writeValue instead",
        replaceWith = ReplaceWith("writeValue(lineBreak = true)"),
        level = DeprecationLevel.WARNING
    )
    override fun writeBreak() = writeValue(lineBreak = true)

    override fun repeat() {
        filterEmptyInput()
        if (hasNextLine()) this.solve()
    }

    private fun filterEmptyInput() {
        while (inputQueue.peek().isEmpty()) inputQueue.pop()
        while (inputQueue.peekLast().isEmpty()) inputQueue.removeLast()
    }

    private fun filterEmptyOutput() {
        while (outputQueue.peek().isEmpty()) outputQueue.pop()
        while (outputQueue.peekLast().isEmpty()) outputQueue.removeLast()
    }


}