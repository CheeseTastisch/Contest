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
        expected = "8"
    }
    file("AdventOfCode2022/in/day8/part2.in")
}){
    var bestScenicScore = 0
    val grid = mutableListOf<List<Int>>()

    while (hasNextLine()) {
        grid.add(readValue(endOfLine = true).chunked(1).map { it.toInt() })
    }

    for (y in 0..grid.lastIndex) {
        for (x in 0..grid[y].lastIndex) {
            var (leftScore, upScore, rightScore, downScore) = listOf(0, 0, 0, 0)

            if (x != 0) {
                for (newX in (0..<x).reversed()) {
                    leftScore++
                    if (grid[y][newX] >= grid[y][x]) break
                }
            }

            if (y != 0) {
                for (newY in (0..<y).reversed()) {
                    upScore++
                    if (grid[newY][x] >= grid[y][x]) break
                }
            }

            if (x != grid[y].lastIndex) {
                for (newX in (x+1..grid[y].lastIndex)) {
                    rightScore++
                    if (grid[y][newX] >= grid[y][x]) break
                }
            }

            if (y != grid.lastIndex) {
                for (newY in (y+1..grid.lastIndex)) {
                    downScore++
                    if (grid[newY][x] >= grid[y][x]) break
                }
            }

            val scenicScore = rightScore * upScore * leftScore * downScore
            if (scenicScore > bestScenicScore) bestScenicScore = scenicScore
        }
    }

    writeValue(bestScenicScore)
}