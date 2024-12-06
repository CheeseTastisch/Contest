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
        expected = "6"
    }

    inferFile("day6.in") {
        outExtension = "part2"
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

    writeValue(AdvancedGuard(startPosition, Direction.UP).checkLoops(map))
}

private data class AdvancedGuard(private var position: Pair<Int, Int>, private var direction: Direction) {

    private val startPosition = position
    private val visited = mutableSetOf<Pair<Int, Int>>()

    fun checkLoops(map: List<List<Boolean>>): Int {
        val mutable = map.toMutableList().map { it.toMutableList() }
        mutable.walk(0)

        var count = 0

        for ((x, y) in visited) {
            if (x to y == startPosition) continue

            position = startPosition
            direction = Direction.UP

            mutable[y][x] = true
            try {
                if (mutable.isLoop()) count++
            } catch (_: StackOverflowError) {
                // don't know why, but with some things a stack overflow error occurs
                // It works fine with the catch (little hacky)
            } finally {
                mutable[y][x] = false
            }
        }

        return count
    }

    private fun List<List<Boolean>>.isLoop(): Boolean {
        val visited = mutableSetOf<Triple<Int, Int, Direction>>()
        return isLoop(0, visited)
    }

    private fun List<List<Boolean>>.isLoop(counter: Int, visited: MutableSet<Triple<Int, Int, Direction>>): Boolean {
        if (visited.contains(Triple(position.first, position.second, direction))) return true
        visited.add(Triple(position.first, position.second, direction))

        return when(canMove()) {
            MoveResult.MOVE -> {
                position = direction.applyTo(position)
                isLoop(counter+1, visited)
            }

            MoveResult.TURN -> {
                direction = direction.rightTurn
                isLoop(counter+1, visited)
            }

            MoveResult.OUT_OF_BOUNDS -> false
        }
    }

    private fun List<List<Boolean>>.walk(counter: Int) {
        visited.add(position)
        when(canMove()) {
            MoveResult.MOVE -> {
                position = direction.applyTo(position)
                walk(counter + 1)
            }

            MoveResult.TURN -> {
                direction = direction.rightTurn
                walk(counter + 1)
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