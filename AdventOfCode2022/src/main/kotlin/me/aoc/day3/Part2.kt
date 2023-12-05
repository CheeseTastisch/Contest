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
        expected = "70"
    }
    file("AdventOfCode2022/in/day3/part2.in")
}){
    var score = 0

    while (hasNextLine()) {
        val contents = MutableList(3) { readValue(endOfLine = true).toList() }

        val duplicates = contents[0].filter { it in contents[1] && it in contents[2] }.distinct()
        for (duplicate in duplicates) {
            if (duplicate.isLowerCase()) score += duplicate - 'a' + 1
            else if (duplicate.isUpperCase()) score += duplicate - 'A' + 27
        }
    }

    writeValue(score)
}