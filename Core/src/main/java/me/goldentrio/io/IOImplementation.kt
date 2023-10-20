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

    override fun readWholeLine(peek: Boolean) =
        if (peek) inputQueue.peek().joinToString(" ")
        else inputQueue.pop().joinToString(" ")

    override fun readLine(peek: Boolean) =
        if (peek) inputQueue.peek().toList()
        else inputQueue.pop().toList()

    override fun readValue(peek: Boolean): String =
        if (peek) inputQueue.peek().peek()
        else inputQueue.peek().pop()

    override fun readInt(peek: Boolean) = readValue(peek).toInt()

    override fun readInts(peek: Boolean) = readLine(peek).map { it.toInt() }

    override fun readDouble(peek: Boolean) = readValue(peek).toDouble()

    override fun readDoubles(peek: Boolean) = readLine(peek).map { it.toDouble() }

    override fun hasNextLine() = inputQueue.isNotEmpty()

    override fun hasNextValue() = inputQueue.peek().isNotEmpty()

    override fun getCurrentLine(): Deque<String> {
        if (outputQueue.isEmpty()) outputQueue.add(ArrayDeque())
        return outputQueue.peekLast()
    }

    override fun writeLine(line: String, lineBreak: Boolean) {
        outputQueue.add(line.split(" ").toCollection(ArrayDeque()))
        if (lineBreak) writeBreak()
    }

    override fun writeValue(value: String, lineBreak: Boolean) {
        getCurrentLine().add(value)
        if (lineBreak) writeBreak()
    }

    override fun writeInt(value: Int, lineBreak: Boolean) = writeValue(value.toString(), lineBreak)

    override fun writeInts(vararg values: Int, lineBreak: Boolean) {
        values.forEach { writeInt(it) }
        if (lineBreak) writeBreak()
    }

    override fun writeDouble(value: Double, lineBreak: Boolean) =
        writeValue(value.toString(), lineBreak)

    override fun writeDoubles(vararg values: Double, lineBreak: Boolean) {
        values.forEach { writeDouble(it) }
        if (lineBreak) writeBreak()
    }

    override fun writeBreak() {
        outputQueue.add(ArrayDeque())
    }

    override fun repeat() {
        if (hasNextLine() && inputQueue.any { it.isNotEmpty() }) {
            filterEmptyInput()
            this.solve()
        }
    }

    private fun filterEmptyInput() {
        while (inputQueue.peek().isEmpty()) inputQueue.pop()
    }

    private fun filterEmptyOutput() {
        while (outputQueue.peek().isEmpty()) outputQueue.pop()
        while (outputQueue.peekLast().isEmpty()) outputQueue.removeLast()
    }


}