package me.aoc.day6

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()) {
        expected = "71503"
    }
    file("AdventOfCode2023/in/day6/part2.in")
}){
    var beating = 0

    val maxTime = readValues().mapNotNull { it.toIntOrNull() }.joinToString("") { it.toString() }.toLong()
    val bestDistance = readValues().mapNotNull { it.toIntOrNull() }.joinToString("") { it.toString() }.toLong()

    for (time in 0..maxTime) {
        val distance = time * (maxTime - time)
        if (distance > bestDistance) beating++
    }

    writeValue(beating)
}