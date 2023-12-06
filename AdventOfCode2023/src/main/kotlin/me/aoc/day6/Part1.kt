package me.aoc.day6

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()) {
        expected = "288"
    }
    file("AdventOfCode2023/in/day6/part1.in")
}){
    var score = 1

    val times = readValues().mapNotNull { it.toIntOrNull() }
    val distances = readValues().mapNotNull { it.toIntOrNull() }

    for (i in times.indices) {
        var beating = 0

        val maxTime = times[i]
        val bestDistance = distances[i]

        for (time in 0..maxTime) {
            val distance = time * (maxTime - time)
            if (distance > bestDistance) beating++
        }

        score *= beating
    }

    writeValue(score)
}