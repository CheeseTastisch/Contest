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
        expected = "3749"
    }

    inferFile("day7.in") {
        outExtension = "part1"
    }
}) {
    var sum = 0L
    while (hasNextLine()) {
        val expected = readValue().substringBefore(":").toLong()
        val line = readLongs()

        val first = line.first()
        val rest = line.drop(1)

        if (isResult(expected, first, rest)) {
            sum += expected
        }
    }

    writeValue(sum)
}

fun isResult(expected: Long, value: Long, operants: List<Long>): Boolean {
    val currentOperant = operants.first()
    val nextOperants = operants.drop(1)

    return Operation.entries.any {
        val newValue = it.apply(value, currentOperant)
        if (newValue == expected && nextOperants.isEmpty()) true
        else if (newValue <= expected && nextOperants.isNotEmpty()) isResult(expected, newValue, nextOperants)
        else false
    }
}

enum class Operation {

    ADD {
        override fun apply(a: Long, b: Long) = a + b
    },

    MULTIPLY {
        override fun apply(a: Long, b: Long) = a * b
    };

    abstract fun apply(a: Long, b: Long): Long

}