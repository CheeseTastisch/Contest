package me.aoc.day2

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        A Y
        B X
        C Z
    """.trimIndent()) {
        expected = "12"
    }
    file("AdventOfCode2022/in/day2/part2.in")
}){
    var score = 0

    while (hasNextLine()) {
        val (otherChar, resultChar) = readValues().map { it[0] }

        val other = Option.byChar(otherChar)
        val result = Result.byChar(resultChar)
        val me = other.getMe(result)

        score += me.value + result.value
    }

    writeValue(score)
}