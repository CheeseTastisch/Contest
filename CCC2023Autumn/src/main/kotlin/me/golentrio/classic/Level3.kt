package me.golentrio.classic

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory
import kotlin.math.abs

fun main() = Contest({
    directory("CCC2023Autumn/classic/lvl3") {
        outExtension = "validator"
        expected("level3_example.in", "level3_example.validator")
    }
}) {
    val size = readInt(endOfLine = true)

    val map = mutableListOf<MutableList<Type>>()
    for (i in 0..<size) {
        map.add(readValue(endOfLine = true).map { Type.byChar(it) }.toMutableList())
    }

    readValue(endOfLine = true)

    while (hasNextLine()) {
        writeValue(
            if (doesCross(size, readValues()
                    .map {
                        it.split(",")
                            .map { v -> v.toInt() }
                            .let { (x, y) -> x to y }
                    })
            ) "INVALID"
            else "VALID",
            lineBreak = true
        )
    }
}

private fun doesCross(size: Int, path: List<Pair<Int, Int>>): Boolean {
    if (path.groupBy { it }.any { (_, list) -> list.size > 1 }) return true

    val pathSet = path.toSet()
    for (index in pathSet.indices) {
        if (index == pathSet.size - 1) break

        val (x, y) = pathSet.elementAt(index)
        if (x >= size || y >= size) continue

        val (nextX, nextY) = pathSet.elementAt(index + 1)
        if (abs(nextX - x) != 1 || abs(nextY - y) != 1) continue

        val checkRight = nextX > x
        val checkLeft = nextX < x
        val checkBottom = nextY > y
        val checkTop = nextY < y

        if (checkRight) {
            val rightIndex = path.indexOf(x + 1 to y)
            if (rightIndex != -1 && rightIndex != pathSet.size - 1) {
                val (rightNextX, rightNextY) = path[rightIndex + 1]

                if (rightNextX == x && rightNextY == if (checkBottom) y + 1 else y - 1) return true
            }
        }

        if (checkLeft) {
            val leftIndex = path.indexOf(x - 1 to y)
            if (leftIndex != -1 && leftIndex != pathSet.size - 1) {
                val (leftNextX, leftNextY) = path[leftIndex + 1]

                if (leftNextX == x && leftNextY == if (checkBottom) y + 1 else y - 1) return true
            }
        }

        if (checkBottom) {
            val bottomIndex = path.indexOf(x to y + 1)
            if (bottomIndex != -1 && bottomIndex != pathSet.size - 1) {
                val (bottomNextX, bottomNextY) = path[bottomIndex + 1]

                if (bottomNextY == y && bottomNextX == if (checkRight) x + 1 else x - 1) return true
            }
        }

        if (checkTop) {
            val topIndex = path.indexOf(x to y - 1)
            if (topIndex != -1 && topIndex != pathSet.size - 1) {
                val (topNextX, topNextY) = path[topIndex + 1]

                if (topNextY == y && topNextX == if (checkRight) x + 1 else x - 1) return true
            }
        }
    }

    return false
}