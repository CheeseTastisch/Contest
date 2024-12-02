package me.aoc.day1

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string("""
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent()) {
        valueSplit = "   "
        expected = "31"
    }

    inferFile("day1.in") {
        valueSplit = "   "
        outExtension = "part2"
    }
}) {
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    while (hasNextLine()) {
        list1.add(readInt())
        list2.add(readInt(endOfLine = true))
    }

    writeValue((list1.indices).fold(0) { acc, i ->
        val number = list1[i]
        acc + list2.count { it == number } * number
    })
}