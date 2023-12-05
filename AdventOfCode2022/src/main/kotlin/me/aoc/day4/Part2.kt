package me.aoc.day4

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string(
        """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()
    ) {
        expected = "4"
    }
    file("AdventOfCode2022/in/day4/part2.in")
}) {
    var score = 0

    while (hasNextLine()) {
        val (range1, range2) = readValue(endOfLine = true)
            .split(",")
            .map { it.split("-") }
            .map { (start, end) -> start.toInt() to end.toInt() }

        if (range1.first in range2.first..range2.second
            || range1.second in range2.first..range2.second
            || range2.first in range1.first..range1.second
            || range2.second in range1.first..range1.second) score++
    }

    writeValue(score)
}