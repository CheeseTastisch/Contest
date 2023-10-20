package me.golentrio.school

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory

fun main() = Contest({
    directory("CCC2023Autumn/school/lvl2") {
        expected("level2_example.in", "level2_example.out")
    }
}) {
    val lines = readInt()
    readLine()

    val pieces = mutableListOf<Piece>()
    while (hasNextLine()) {
        val line = readWholeLine().split(",")
        pieces.add(
            Piece(
                Type.byType(line[0][0]),
                Type.byType(line[1][0]),
                Type.byType(line[2][0]),
                Type.byType(line[3][0]),
        ))
    }

    pieces
        .groupByCompare { first, second -> first.equalsIgnoreOrientation(second) }
        .forEach { piece, list ->
            writeLine("${list.size} ${piece.top.type},${piece.right.type},${piece.bottom.type},${piece.left.type}")
        }
}