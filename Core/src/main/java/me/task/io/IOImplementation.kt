package me.task.io

import me.task.source.Source
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

    override fun readAllValues(): List<String> {
        val values = mutableListOf<String>()
        while (hasNextLine()) values.addAll(readValues())
        return values
    }

    override fun writeValue(vararg values: Any, append: Boolean, lineBreak: Boolean) {
        if (!append && outputQueue.peekLast().isNotEmpty()) outputQueue.add(LinkedList())

        values.forEach { getCurrentLine().add(it.toString()) }

        if (lineBreak) outputQueue.add(LinkedList())
    }

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