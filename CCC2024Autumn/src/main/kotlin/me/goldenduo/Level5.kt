package me.goldenduo

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    file("CCC2024Autumn/in/lvl5/level5_5.in")
//    directory("CCC2024Autumn/in/lvl5") {
//        expected("level5_example.in", "level5_example.out")
//    }
//    string("""
//        1
//        7 7 10
//    """.trimIndent())
}) {
    readValues()

    while (hasNextLine()) {
        val (x, y, amount) = readInts()

        val grid = Grid((1..y).map { _ ->
            (1..x).map { CellType.FREE }.toMutableList()
        })

        grid.solve()

        val real = (grid.map.sumOf { row ->
            row.count { it == CellType.TABLE }
        }) / 2

        if (real != amount) {
            println("Expected $amount, got $real")
        }

        for (row in grid.map) {
            writeValue(row.map { it.output }.joinToString(""), lineBreak = true)
        }
        writeValue(lineBreak = true)
    }
}

enum class Direction(
    val place: Set<Pair<Int, Int>>,
    val block: Set<Pair<Int, Int>>,
    val delta: Pair<Int, Int>,
) {

    DOWN(
        setOf(0 to 0, 1 to 0),
        setOf(-1 to -1, 0 to -1, 1 to -1, 2 to -1, -1 to 0, 2 to 0, -1 to 1, 0 to 1, 1 to 1, 2 to 1),
        0 to 1,
    ) {
        override fun next(): Direction = RIGHT
    },
    RIGHT(
        setOf(0 to 0, 0 to -1),
        setOf(-1 to -2, 0 to -2, 1 to -2, -1 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1),
        1 to 0,
    ) {
        override fun next(): Direction = UP
    },
    UP(
        setOf(0 to 0, -1 to 0),
        setOf(-2 to -1, -1 to -1, 0 to -1, 1 to -1, -2 to 0, 1 to 0, -2 to 1, -1 to 1, 0 to 1, 1 to 1),
        0 to -1,
    ) {
        override fun next(): Direction = LEFT
    },
    LEFT(
        setOf(0 to 0, 0 to 1),
        setOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 1 to 1, -1 to 2, 0 to 2, 1 to 2),
        -1 to 0,
    ) {
        override fun next(): Direction = DOWN
    }
    ;

    abstract fun next(): Direction

}

data class Pointer(
    var x: Int,
    var y: Int,
    var direction: Direction,
) {

    fun move(amount: Int) {
        x += direction.delta.first * amount
        y += direction.delta.second * amount
    }

    fun rotate() {
        direction = direction.next()
    }

}

data class Grid(val map: List<MutableList<CellType>>) {

    private val width: Int = map[0].size
    private val height: Int = map.size

    val pointer = Pointer(0, 0, Direction.DOWN)

    val Pointer.placeable: Boolean
        get() =
            direction.place.all {
                inBounds(x + it.first, y + it.second) &&
                        get(x + it.first, y + it.second) == CellType.FREE
            } &&
                    direction.block.all {
                        !inBounds(x + it.first, y + it.second) ||
                        get(x + it.first, y + it.second) != CellType.TABLE
                    }

    fun Pointer.place() {
        direction.place.forEach { (dx, dy) ->
            set(x + dx, y + dy, CellType.TABLE)
        }

        direction.block.forEach { (dx, dy) ->
            set(x + dx, y + dy, CellType.BLOCKED)
        }
    }

    fun inBounds(x: Int, y: Int): Boolean {
        return x in 0..<width && y in 0..<height
    }

    fun setPointerTopLeft() {
        val (topLeft) = getEdges()
        pointer.x = topLeft.first
        pointer.y = topLeft.second
        pointer.direction = Direction.DOWN

        val (width, height) = getWidthAndHeight() ?: return
        if (width == 3 && height > 1) {
            pointer.y += 1
            pointer.direction = Direction.RIGHT
        }

        if (width == 1) {
            pointer.y += 1
            pointer.direction = Direction.RIGHT
        }
    }

    val Pointer.isAlmostTouchingEdge: Boolean
        get() {
            val lookingAtX = x + direction.delta.first
            val lookingAtY = y + direction.delta.second

            val lookingAtX2 = x + direction.delta.first * 2
            val lookingAtY2 = y + direction.delta.second * 2

            if (!inBounds(lookingAtX, lookingAtY)) return false
            if (!inBounds(lookingAtX2, lookingAtY2)) return true

            if (get(lookingAtX, lookingAtY) != CellType.FREE) return false
            if (get(lookingAtX2, lookingAtY2) == CellType.FREE) return false

            return true
        }

    operator fun get(x: Int, y: Int): CellType {
        if (!inBounds(x, y)) throw IndexOutOfBoundsException()
        return map[y][x]
    }

    operator fun set(x: Int, y: Int, value: CellType) {
        if (!inBounds(x, y)) return
        map[y][x] = value
    }

    fun getFreeCells(): List<Pair<Int, Int>> {
        return map.flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, cell ->
                if (cell == CellType.FREE) x to y else null
            }
        }
    }

    fun isRectangleLeft(): Boolean {
        val freeCells = getFreeCells()

        if (freeCells.isEmpty()) return false

        val minX = freeCells.minOf { it.first }
        val allWithMinX = freeCells.filter { it.first == minX }
        val minXminY = allWithMinX.minOf { it.second }
        val minXmaxY = allWithMinX.maxOf { it.second }

        val maxX = freeCells.maxOf { it.first }
        val allWithMaxX = freeCells.filter { it.first == maxX }
        val maxXminY = allWithMaxX.minOf { it.second }
        val maxXmaxY = allWithMaxX.maxOf { it.second }

        val minY = freeCells.minOf { it.second }
        val allWithMinY = freeCells.filter { it.second == minY }
        val minYminX = allWithMinY.minOf { it.first }
        val minYmaxX = allWithMinY.maxOf { it.first }

        val maxY = freeCells.maxOf { it.second }
        val allWithMaxY = freeCells.filter { it.second == maxY }
        val maxYminX = allWithMaxY.minOf { it.first }
        val maxYmaxX = allWithMaxY.maxOf { it.first }

        if (minXminY != maxXminY) return false
        if (minXmaxY != maxXmaxY) return false
        if (minYminX != maxYminX) return false
        if (minYmaxX != maxYmaxX) return false
        return true
    }

    fun getEdges(): List<Pair<Int, Int>> {
        val topLeft = map.flatten().indexOfFirst { it == CellType.FREE }
        val left = topLeft % width
        val top = topLeft / width

        val right = map
            .firstOrNull { it.contains(CellType.FREE) }
            ?.indexOfLast { it == CellType.FREE }
            ?: 0

        val bottom = map
            .indexOfLast { it.contains(CellType.FREE) }

        return listOf(
            left to top,
            right to top,
            left to bottom,
            right to bottom,
        )
    }

    fun getWidthAndHeight(): Pair<Int, Int>? {
        if (!isRectangleLeft()) return null

        val (topLeft, topRight, bottomLeft, _) = getEdges()
        return topRight.first - topLeft.first + 1 to bottomLeft.second - topLeft.second + 1
    }

    fun solve() {
        setPointerTopLeft()
        if(!pointer.placeable) return

        while (pointer.placeable) {
            if (isRectangleLeft()) {
                val (width, height) = getWidthAndHeight()!!

                if (width == 2 && height == 2) {
                    setPointerTopLeft()
                    if (pointer.placeable) pointer.place()
                    return
                }

                if (width == 3 && height > 1 || width == 1 || width > 1 && height == 3) {
                    setPointerTopLeft()
                }
            }

            if (pointer.isAlmostTouchingEdge) {
                pointer.move(1)
                pointer.rotate()
            } else {
                pointer.place()
                pointer.move(2)
            }
        }

        solve()
    }


}