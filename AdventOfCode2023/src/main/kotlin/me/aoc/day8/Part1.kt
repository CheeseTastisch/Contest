package me.aoc.day8

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        RL

        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()) {
        expected = "2"
    }
    file("AdventOfCode2023/in/day8/part1.in")
}){
    val instructions = readValue(endOfLine = true).toCharArray().map {
        when (it) {
            'R' -> true
            else -> false
        }
    }
    readValue(endOfLine = true)

    val nodes = mutableMapOf<String, Pair<String, String>>()
    while (hasNextLine()) {
        val node = readValue()
        readValue()
        val (key, value) = readValues()
        nodes[node] = key.substring(1, key.length - 1) to value.substring(0, value.length - 1)
    }

    var current = "AAA"
    var steps = 0
    var counter = 0

    while (current != "ZZZ") {
        val right = instructions[counter]
        val (left, next) = nodes[current]!!
        current = if (right) next else left

        steps++
        counter = (counter + 1) % instructions.size
    }

    writeValue(steps)
}