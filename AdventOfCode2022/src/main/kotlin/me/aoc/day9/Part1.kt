package me.aoc.day9

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string
import kotlin.math.absoluteValue

fun main() = Task({
    string("""
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()) {
        expected = "13"
    }
    file("AdventOfCode2022/in/day9/part1.in")
}){
    val moves = mutableListOf<Pair<Direction, Int>>()
    while (hasNextLine()) {
        val direction = Direction.byIdentifier(readValue().first())
        val distance = readInt(endOfLine = true)

        moves.add(direction to distance)
    }

    var head = 0 to 0
    var tail = 0 to 0
    val visited = mutableSetOf<Pair<Int, Int>>()

    for ((direction, distance) in moves) {
        for (i in 0..<distance) {
            head = head.first + direction.motion.first to head.second + direction.motion.second

            val deltaX = head.first - tail.first
            val deltaY = head.second - tail.second

            if (deltaX !in -1..1 || deltaY !in -1..1) {
                tail = when {
                    deltaX == 0 -> {
                        val y = tail.second + if (deltaY > 1) 1 else -1
                        tail.first to y
                    }

                    deltaY == 0 -> {
                        val x = tail.first + if (deltaX > 1) 1 else -1
                        x to tail.second
                    }

                    else -> {
                        val x = tail.first + deltaX / deltaX.absoluteValue
                        val y = tail.second + deltaY / deltaY.absoluteValue
                        x to y
                    }
                }
            }

            visited.add(tail)
        }
    }

    writeValue(visited.size)
}