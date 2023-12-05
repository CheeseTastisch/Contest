package me.aoc.day8

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()) {
        expected = "21"
    }
    file("AdventOfCode2022/in/day8/part1.in")
}){
    var visible = 0
    val grid = mutableListOf<List<Int>>()

    while (hasNextLine()) {
        grid.add(readValue(endOfLine = true).chunked(1).map { it.toInt() })
    }

    for (y in 0..grid.lastIndex) {
        for (x in 0..grid[y].lastIndex) {
            if (x == 0 || y == 0 || x == grid[y].lastIndex || y == grid.lastIndex) {
                visible++
                continue
            }

            if((0..<x).all { grid[y][it] < grid[y][x] }) {
                visible++
                continue
            }

            if((0..<y).all { grid[it][x] < grid[y][x] }) {
                visible++
                continue
            }

            if((x+1..grid[y].lastIndex).all { grid[y][it] < grid[y][x] }) {
                visible++
                continue
            }

            if((y+1..grid.lastIndex).all { grid[it][x] < grid[y][x] }) {
                visible++
                continue
            }
        }
    }

    writeValue(visible)
}