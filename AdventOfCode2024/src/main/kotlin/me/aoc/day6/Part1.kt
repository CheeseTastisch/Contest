package me.aoc.day6

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string("""
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent()) {
        expected = "41"
    }

    inferFile("day6.in") {
        outExtension = "part1"
    }
}) {
    val map = mutableListOf<List<Boolean>>()

    var startPosition = 0 to 0

    while (hasNextLine()) {
        readValue(endOfLine = true).let { line ->
            if (line.contains("^")) startPosition = line.indexOf("^") to map.size
            map.add(line.map { it == '#' })
        }
    }

    writeValue(Guard(startPosition, Direction.UP).checkVisited(map))
}

data class Guard(private var position: Pair<Int, Int>, private var direction: Direction) {

    private val visited = mutableSetOf<Pair<Int, Int>>()

    fun checkVisited(map: List<List<Boolean>>): Int {
        map.move()
        return visited.size
    }

    private fun List<List<Boolean>>.move() {
        visited.add(position)
        when(canMove()) {
            MoveResult.MOVE -> {
                position = direction.applyTo(position)
                move()
            }

            MoveResult.TURN -> {
                direction = direction.rightTurn
                move()
            }

            MoveResult.OUT_OF_BOUNDS -> {}
        }
    }

    private fun List<List<Boolean>>.canMove(): MoveResult {
        val nextPosition = direction.applyTo(position)
        val hasObstacle = getOrNull(nextPosition.second)?.getOrNull(nextPosition.first)

        return when(hasObstacle) {
            true -> MoveResult.TURN
            false -> MoveResult.MOVE
            null -> MoveResult.OUT_OF_BOUNDS
        }
    }

}

enum class Direction(val delta: Pair<Int, Int>) {
    UP(0 to -1),
    RIGHT(1 to 0),
    DOWN(0 to 1),
    LEFT(-1 to 0);

    val rightTurn: Direction
        get() = when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }

    fun applyTo(position: Pair<Int, Int>) = position.first + delta.first to position.second + delta.second

}

enum class MoveResult {

    MOVE,
    TURN,
    OUT_OF_BOUNDS,

}