package me.aoc.day9

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string
import kotlin.math.absoluteValue

fun main() = Task({
    string("""
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()) {
        expected = "36"
    }
    file("AdventOfCode2022/in/day9/part2.in")
}){
    data class Knot(
        var position: Pair<Int, Int>,
        val follows: Knot?,
        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    ) {

        fun move(direction: Direction) {
            position = position.first + direction.motion.first to position.second + direction.motion.second
        }

        fun follow() {
            val deltaX = follows!!.position.first - position.first
            val deltaY = follows.position.second - position.second

            if (deltaX !in -1..1 || deltaY !in -1..1) {
                position = when {
                    deltaX == 0 -> {
                        val y = position.second + if (deltaY > 1) 1 else -1
                        position.first to y
                    }

                    deltaY == 0 -> {
                        val x = position.first + if (deltaX > 1) 1 else -1
                        x to position.second
                    }

                    else -> {
                        val x = position.first + deltaX / deltaX.absoluteValue
                        val y = position.second + deltaY / deltaY.absoluteValue
                        x to y
                    }
                }
            }

            visited.add(position)
        }

    }

    val moves = mutableListOf<Pair<Direction, Int>>()
    while (hasNextLine()) {
        val direction = Direction.byIdentifier(readValue().first())
        val distance = readInt(endOfLine = true)

        moves.add(direction to distance)
    }

    val head = Knot(0 to 0, null)
    val knots = mutableListOf<Knot>()
    for (i in 1..9) {
        val knot = Knot(0 to 0, if (knots.isEmpty()) head else knots.last())
        knots.add(knot)
    }

    for ((direction, distance) in moves) {
        for (i in 0..<distance) {
            head.move(direction)
            knots.forEach { it.follow() }
        }
    }

    writeValue(knots.last().visited.size)
}