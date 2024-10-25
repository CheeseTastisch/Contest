package me.goldenduo

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.string
import javax.swing.text.TableView.TableRow

fun main() = Task({
    directory("CCC2024Autumn/in/lvl4") {
        expected("level4_example.in", "level4_example.out")
    }
}){
    readValues()

    while (hasNextLine()) {
        val (x, y, _) = readInts()

        val map = (1..y).map { _ ->
            (1..x).map {
                CellType.FREE
            }
        }

        val horizontal = map.toMutableList().map { it.toMutableList() }
        for(topY in (0..<y) step 2) {
            for(topX in (0..<x) step 4) {
                if (topX + 2 >= x) {
                    continue
                }

                setOf(0, 1, 2).forEach { delta ->
                    horizontal[topY][topX + delta] = CellType.TABLE
                }

                setOf(
                    -1 to -1, 0 to -1, 1 to -1, 2 to -1, 3 to -1,
                    -1 to 0, 3 to 0,
                    -1 to 1, 0 to 1, 1 to 1, 2 to 1, 3 to 1,
                ).forEach { (deltaX, deltaY) ->
                    horizontal.getOrNull(topY + deltaY)?.let {
                        if ((topX + deltaX) in it.indices) {
                            it[topX + deltaX] = CellType.BLOCKED
                        }
                    }
                }

            }
        }

        if (horizontal[0][x - 1] == CellType.FREE) {
            val topX = x - 1
            for (topY in (0..<y) step 4) {
                if (topY + 2 >= y) {
                    continue
                }

                setOf(0, 1, 2).forEach { delta ->
                    horizontal[topY + delta][topX] = CellType.TABLE
                }

                setOf(
                    -1 to -1, -1 to 0, -1 to 1, -1 to 2, -1 to 3,
                    0 to -1, 0 to 3,
                    1 to -1, 1 to 0, 1 to 1, 1 to 2, 1 to 3,
                ).forEach { (deltaX, deltaY) ->
                    horizontal.getOrNull(topY + deltaY)?.let {
                        if ((topX + deltaX) in it.indices) {
                            it[topX + deltaX] = CellType.BLOCKED
                        }
                    }
                }
            }
        }

        val vertical = map.toMutableList().map { it.toMutableList() }
        for(topY in (0..<y) step 4) {
            for(topX in (0..<x) step 2) {
                if (topY + 2 >= y) {
                    continue
                }

                setOf(0, 1, 2).forEach { delta ->
                    vertical[topY + delta][topX] = CellType.TABLE
                }

                setOf(
                    -1 to -1, -1 to 0, -1 to 1, -1 to 2, -1 to 3,
                    0 to -1, 0 to 3,
                    1 to -1, 1 to 0, 1 to 1, 1 to 2, 1 to 3,
                ).forEach { (deltaX, deltaY) ->
                    vertical.getOrNull(topY + deltaY)?.let {
                        if ((topX + deltaX) in it.indices) {
                            it[topX + deltaX] = CellType.BLOCKED
                        }
                    }
                }

            }
        }

        if (vertical[y - 1][0] == CellType.FREE) {
            val topY = y - 1
            for (topX in (0..<y) step 4) {
                if (topX + 2 >= x) {
                    continue
                }

                setOf(0, 1, 2).forEach { delta ->
                    vertical[topY][topX + delta] = CellType.TABLE
                }

                setOf(
                    -1 to -1, 0 to -1, 1 to -1, 2 to -1, 3 to -1,
                    -1 to 0, 3 to 0,
                    -1 to 1, 0 to 1, 1 to 1, 2 to 1, 3 to 1,
                ).forEach { (deltaX, deltaY) ->
                    vertical.getOrNull(topY + deltaY)?.let {
                        if ((topX + deltaX) in it.indices) {
                            it[topX + deltaX] = CellType.BLOCKED
                        }
                    }
                }
            }
        }

        if (horizontal.countTables() > vertical.countTables()) {
            for (horizontalRow in horizontal) {
                writeValue(horizontalRow.map { it.output }.joinToString(""), lineBreak = true)
            }
        } else {
            for (verticalRow in vertical) {
                writeValue(verticalRow.map { it.output }.joinToString(""), lineBreak = true)
            }
        }

        writeValue(lineBreak = true)
    }
}

fun List<List<CellType>>.countTables() = sumBy { row ->
    row.count { it == CellType.TABLE }
}

enum class CellType(val output: Char) {
    TABLE('X'),
    BLOCKED('!'),
    FREE('.'),
}