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
        this.solve()
        source.writeOutput(outputQueue)
    }

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

    override fun writeLine(line: String) {
        outputQueue.add(line.split(" ").toCollection(ArrayDeque()))
    }

    override fun writeValue(value: String) {
        getCurrentLine().add(value)
    }

    override fun writeInt(value: Int) = writeValue(value.toString())

    override fun writeInts(vararg values: Int) = values.forEach { writeInt(it) }

    override fun writeDouble(value: Double) = writeValue(value.toString())

    override fun writeDoubles(vararg values: Double) = values.forEach { writeDouble(it) }

    override fun writeBreak() {
        outputQueue.add(ArrayDeque())
    }

    override fun repeat() {
        if (hasNextLine()) this.solve()
    }


}