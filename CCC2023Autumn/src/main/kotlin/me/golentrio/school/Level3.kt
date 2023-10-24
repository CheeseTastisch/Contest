package me.golentrio.school

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory

fun main() = Contest({
    directory("CCC2023Autumn/school/lvl3") {
        outExtension = "iloveccc"
        expected("level3_example.in", "level3_example.out")
    }
}) {
    readValues()

    val puzzle = mutableListOf<MutableList<Piece>>()
    puzzle.add(mutableListOf())

    while (hasNextLine()) {
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

        puzzle.add(mutableListOf())
        if (hasNextLine()) readValues()
    }

    for (i in puzzle.indices) {
        for (j in puzzle[i].indices) {
            val piece = puzzle[i][j]

            if (piece.right != Type.EDGE) {
                val right = puzzle[i][j + 1]
                if (piece.right == right.left) piece.right = piece.right.invert()
            }

            if (piece.bottom != Type.EDGE) {
                val bottom = puzzle[i + 1][j]
                if (piece.bottom == bottom.top) piece.bottom = piece.bottom.invert()
            }
        }
    }

    puzzle.forEach { row ->
        row.forEach { writeValue("${it.top},${it.right},${it.bottom},${it.left}") }
        writeValue(lineBreak = true)
    }
}