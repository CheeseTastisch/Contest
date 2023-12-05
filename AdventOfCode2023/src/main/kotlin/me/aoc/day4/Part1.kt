package me.aoc.day4

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string
import kotlin.math.pow

fun main() = Task({
    string("""
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent()) {
        expected = "13"
    }
    file("AdventOfCode2023/in/day4/part1.in")
}) {
    var points = 0
    while (hasNextLine()) {
        readValue()
        readValue()

        val winning = mutableListOf<Int>()
        while (hasNextValue() && readValue(peek = true) != "|") {
            val card = readValue().toIntOrNull() ?: continue
            winning.add(card)
        }

        readValue()
        val winningAmount = readValues().mapNotNull { it.toIntOrNull() }.count { it in winning }
        if (winningAmount != 0) points += 2.0.pow(winningAmount - 1).toInt()
    }
    writeValue(points)
}