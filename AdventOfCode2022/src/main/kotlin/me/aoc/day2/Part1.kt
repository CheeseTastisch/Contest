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
        expected = "15"
    }
    file("AdventOfCode2022/in/day2/part1.in")
}){
    var score = 0

    while (hasNextLine()) {
        val (other, me) = readValues().map { Option.byChar(it[0]) }
        score += me.value + me.getScore(other)
    }

    writeValue(score)
}