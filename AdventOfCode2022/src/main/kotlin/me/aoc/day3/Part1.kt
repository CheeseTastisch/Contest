package me.aoc.day3

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()) {
        expected = "157"
    }
    file("AdventOfCode2022/in/day3/part1.in")
}){
    var score = 0
    while (hasNextLine()) {
        val line = readValue(endOfLine = true)
        val compartments = line.chunked(line.length / 2).map { it.toList() }

        val duplicates = compartments[0].filter { it in compartments[1] }.distinct()
        for (duplicate in duplicates) {
            if (duplicate.isLowerCase()) score += duplicate - 'a' + 1
            else if (duplicate.isUpperCase()) score += duplicate - 'A' + 27
        }
    }

    writeValue(score)
}