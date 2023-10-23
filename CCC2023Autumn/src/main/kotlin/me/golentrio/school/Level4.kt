package me.golentrio.school

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory
import me.goldentrio.source.standard.file
import me.goldentrio.util.tupple.MutablePair

fun main() = Contest({
//    file("CCC2023Autumn/school/lvl4/level4_example.in") {
//        expected("CCC2023Autumn/school/lvl4/level4_example.out")
//    }
    directory("CCC2023Autumn/school/lvl4") {
        expected("level4_example.in", "level4_example.out")
    }
}) {
    val pieces = readInt()
    readLine()

    val allowedPieces = mutableListOf<Piece>()
    for (i in 0..<pieces) {
        val line = readWholeLine().split(",")
        allowedPieces.add(
            Piece(
                Type.byType(line[0][0]),
                Type.byType(line[1][0]),
                Type.byType(line[2][0]),
                Type.byType(line[3][0]),
            )
        )
    }

    val size = readInt(endOfLine = true)

    val puzzle = mutableListOf<MutableList<Piece?>>()
    puzzle.add(mutableListOf())

    for (i in 0..<size) {
        if (i == 0 || i == size - 1) {
            while (hasNextValue()) {
                val piece = readValue().split(",")
                puzzle.last().add(
                    Piece(
                        Type.byType(piece[0][0]),
                        Type.byType(piece[1][0]),
                        Type.byType(piece[2][0]),
                        Type.byType(piece[3][0]),
                    )
                )
            }
        } else {
            var piece = readValue().split(",")
            puzzle.last().add(
                Piece(
                    Type.byType(piece[0][0]),
                    Type.byType(piece[1][0]),
                    Type.byType(piece[2][0]),
                    Type.byType(piece[3][0]),
                )
            )

            for (j in 0..(size - 3)) puzzle.last().add(null)

            piece = inputQueue.peek().last().split(",")
            puzzle.last().add(
                Piece(
                    Type.byType(piece[0][0]),
                    Type.byType(piece[1][0]),
                    Type.byType(piece[2][0]),
                    Type.byType(piece[3][0]),
                )
            )
        }

        if (hasNextLine()) {
            readLine()
            puzzle.add(mutableListOf())
        }
    }
    if (puzzle.last().isEmpty()) puzzle.removeLast()

    bruteForce(size, puzzle, allowedPieces.map { MutablePair(it, true) })

    puzzle.forEach { row ->
        row.forEach {
            writeValue("${it?.top},${it?.right},${it?.bottom},${it?.left}")
        }
        writeBreak()
    }
}

fun bruteForce(
    size: Int,
    puzzle: MutableList<MutableList<Piece?>>,
    allowed: List<MutablePair<Piece, Boolean>>,
    row: Int = 0,
    col: Int = 0,
): Boolean {
    if (row == size && col == 0) return true

    val nextRow = if (col == size - 1) row + 1 else row
    val nextCol = if (col == size - 1) 0 else col + 1

    if (puzzle[row][col] != null) return bruteForce(size, puzzle, allowed, nextRow, nextCol)

    val tried = mutableListOf<Piece>()
    for (piece in allowed) {
        if (!piece.second) continue

        puzzle[row][col] = piece.first
        piece.second = false

        repeat(4) {
            piece.first.rotate()

            if (piece.first in tried) return@repeat

            if (fits(row, col, piece.first, puzzle) && bruteForce(size, puzzle, allowed, nextRow, nextCol)) return true
            else tried.add(piece.first.copy())
        }

        puzzle[row][col] = null
        piece.second = true
    }

    return false
}


fun fits(row: Int, col: Int, piece: Piece, puzzle: MutableList<MutableList<Piece?>>): Boolean {
    val above = puzzle.getOrNull(row - 1)?.getOrNull(col)
    if (above != null && !above.bottom.fits(piece.top)) return false

    val right = puzzle.getOrNull(row)?.getOrNull(col + 1)
    if (right != null && !right.left.fits(piece.right)) return false

    val below = puzzle.getOrNull(row + 1)?.getOrNull(col)
    if (below != null && !below.top.fits(piece.bottom)) return false

    val left = puzzle.getOrNull(row)?.getOrNull(col - 1)
    return !(left != null && !left.right.fits(piece.left))
}
