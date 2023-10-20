package me.golentrio.school

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory
import me.goldentrio.source.standard.file

fun main() = Contest({
    file("CCC2023Autumn/school/lvl4/level4_example.in") {
        expected("CCC2023Autumn/school/lvl4/level4_example.out")
    }
//    directory("CCC2023Autumn/school/lvl4")
}) {
    val pieces = readInt()
    readLine()

    val allowedPieces = mutableListOf<Piece>()
    for (i in 0..<pieces) {
        val line = readValue().split(",")
        readLine()
        allowedPieces.add(
            Piece(
                Type.byType(line[0][0]),
                Type.byType(line[1][0]),
                Type.byType(line[2][0]),
                Type.byType(line[3][0]),
            )
        )
    }

    val size = readInt()
    readLine()

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

        if (hasNextLine()) readLine()
        puzzle.add(mutableListOf())
    }
    puzzle.removeLast()

    println(puzzle)

    solvePuzzle(1, 1, puzzle, allowedPieces)

    puzzle.forEach { row ->
        row.forEach {
            writeValue("${it?.top},${it?.right},${it?.bottom},${it?.left}")
        }
        writeBreak()
    }
}

fun solvePuzzle(row: Int, col: Int, puzzle: MutableList<MutableList<Piece?>>, remainingPieces: MutableList<Piece>): Boolean {
    if (row == puzzle.size) return true

    val nextRow = if (col == puzzle[row].size - 1) row + 1 else row
    val nextCol = if (col == puzzle[row].size - 1) 0 else col + 1

    if (puzzle[row][col] != null) return solvePuzzle(nextRow, nextCol, puzzle, remainingPieces)

    for (piece in remainingPieces.toMutableList()) {
        if (fits(row, col, piece, puzzle)) {
            puzzle[row][col] = piece
            remainingPieces.remove(piece)

            if (solvePuzzle(nextRow, nextCol, puzzle, remainingPieces)) return true

            puzzle[row][col] = null
            remainingPieces.add(piece)
        }
    }

    return false
}

fun fits(row: Int, col: Int, piece: Piece, puzzle: MutableList<MutableList<Piece?>>): Boolean {
    if (piece.top != Type.EDGE) {
        val above = puzzle[row - 1][col]
        if (above != null && above.bottom == piece.top) return false
    }

    if (piece.right != Type.EDGE) {
        val right = puzzle[row][col + 1]
        if (right != null && right.left == piece.right) return false
    }

    if (piece.bottom != Type.EDGE) {
        val below = puzzle[row + 1][col]
        if (below != null && below.top == piece.bottom) return false
    }

    if (piece.left != Type.EDGE) {
        val left = puzzle[row][col - 1]
        if (left != null && left.right == piece.left) return false
    }

    return true
}
