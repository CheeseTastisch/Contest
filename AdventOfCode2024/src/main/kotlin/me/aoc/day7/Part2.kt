package me.aoc.day7

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string("""
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent()) {
        expected = "11387"
    }

    inferFile("day7.in") {
        outExtension = "part2"
    }
}) {
    var sum = 0L
    while (hasNextLine()) {
        val expected = readValue().substringBefore(":").toLong()
        val line = readLongs()

        val first = line.first()
        val rest = line.drop(1)

        if (isFoldableResult(expected, first, rest)) sum += expected
    }

    writeValue(sum)
}

fun isFoldableResult(expected: Long, value: Long, operants: List<Long>): Boolean {
    val currentOperant = operants.first()
    val nextOperants = operants.drop(1)

    return FoldableOperation.entries.any {
        val newValue = it.apply(value, currentOperant)
        if (newValue == expected && nextOperants.isEmpty()) true
        else if (newValue <= expected && nextOperants.isNotEmpty()) isFoldableResult(expected, newValue, nextOperants)
        else false
    }
}

enum class FoldableOperation {

    ADD {
        override fun apply(a: Long, b: Long) = a + b
    },

    MULTIPLY {
        override fun apply(a: Long, b: Long) = a * b
    },

    FOLD {
        override fun apply(a: Long, b: Long) = (a.toString() + b.toString()).toLong()
    };

    abstract fun apply(a: Long, b: Long): Long

}