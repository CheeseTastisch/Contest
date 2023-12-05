package me.aoc.day1

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent()) {
        expected = "142"
    }
    file("AdventOfCode2023/in/day1/part1.in")
}) {
    var value = 0
    while (hasNextLine()) {
        val line = readValue(endOfLine = true)

        val firstDigit = line.first { it.isDigit() }.toString().toInt()
        val lastDigit = line.last { it.isDigit() }.toString().toInt()

        value += firstDigit * 10 + lastDigit
    }
    writeValue(value)
}