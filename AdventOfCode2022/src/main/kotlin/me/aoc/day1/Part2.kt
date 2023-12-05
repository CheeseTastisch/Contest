package me.aoc.day1

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent()) {
        expected = "45000"
    }
    file("AdventOfCode2022/in/day1/part2.in")
}){
    val calories = mutableListOf<Int>()
    var currentCalories = 0

    while (hasNextLine()) {
        val value = readValue(endOfLine = true).toIntOrNull()
        if (value == null) {
            calories.add(currentCalories)
            currentCalories = 0
        } else {
            currentCalories += value
        }
    }
    calories.add(currentCalories)

    writeValue(calories.sortedDescending().take(3).sum())
}