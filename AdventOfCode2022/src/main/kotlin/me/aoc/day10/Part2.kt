package me.aoc.day10

import me.task.Task
import me.task.source.standard.file

fun main() = Task({
    file("AdventOfCode2022/in/day10/example.part2.in") {
        expected("AdventOfCode2022/in/day10/example.part2.out")
    }
    file("AdventOfCode2022/in/day10/part2.in")
}) {
    var currentX = 1
    val xHistory = mutableListOf<Int>()

    while (hasNextLine()) {
        val statement = readValue()

        when (statement) {
            "noop" -> xHistory.add(currentX)
            "addx" -> {
                xHistory.add(currentX)
                xHistory.add(currentX)
                currentX += readInt()
            }
        }

        readValues()
    }

    val canvas = buildString {
        for (y in 0..<6) {
            for (x in 0..<40) {
                val xValue = xHistory[y * 40 + x]
                if (x in xValue - 1..xValue + 1) append("#")
                else append(".")
            }
            appendLine()
        }
    }

    writeValue(canvas)
}