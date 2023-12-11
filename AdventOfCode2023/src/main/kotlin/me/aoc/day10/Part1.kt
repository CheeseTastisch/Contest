package me.aoc.day10

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        -L|F7
        7S-7|
        L|7||
        -L-J|
        L|-JF
    """.trimIndent()) {
        expected = "4"
    }
    string("""
        7-F7-
        .FJ|7
        SJLL7
        |F--J
        LJ.LJ
    """.trimIndent()) {
        expected = "8"
    }
    file("AdventOfCode2023/in/day10/part1.in")
}){
    val parts = mutableListOf<Part>()

    var x: Int
    var y = 0
    while (hasNextLine()) {
        x = 0
        readValue(endOfLine = true).forEach {
            parts.add(Part(x, y, PartType.fromIdentifier(it)))
            x++
        }

        y++
    }

    val grid = Grid(parts)
    writeValue(grid.distanceLoop.values.max())
}