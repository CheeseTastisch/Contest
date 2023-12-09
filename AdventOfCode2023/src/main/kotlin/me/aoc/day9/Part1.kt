package me.aoc.day9

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent()) {
        expected = "114"
    }
    file("AdventOfCode2023/in/day9/part1.in")
}){
    var value = 0

    while (hasNextLine()) {
        val differences = mutableListOf(readInts())

        while (differences.last().any { it != 0 }) {
            val newDifferences = mutableListOf<Int>()
            val lastDifferences = differences.last()

            for (i in 0..<lastDifferences.lastIndex) {
                val difference = lastDifferences[i + 1] - lastDifferences[i]
                newDifferences.add(difference)
            }

            differences.add(newDifferences)
        }

        var currentIndex = differences.lastIndex
        var currentValue = 0

        while (currentIndex >= 0) {
            currentValue += differences[currentIndex].last()
            currentIndex--
        }

        value += currentValue
    }

    writeValue(value)
}