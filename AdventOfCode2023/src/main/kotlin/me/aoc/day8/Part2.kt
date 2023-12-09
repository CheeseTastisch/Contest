package me.aoc.day8

import me.aoc.util.ChangingValue
import me.aoc.util.modify
import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent()) {
        expected = "6"
    }
    file("AdventOfCode2023/in/day8/part2.in")
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

    val currents = nodes.filter { (k, _) -> k.endsWith("A") }.map { (k, _) -> ChangingValue(k) }
    var steps = 0
    var counter = 0

    while (currents.any { !it.value.endsWith("Z") }) {
        val right = instructions[counter]

        currents.modify {
            if (right) nodes[it]!!.second
            else nodes[it]!!.first
        }

        steps++
        counter = (counter + 1) % instructions.size
    }

    writeValue(steps)
}