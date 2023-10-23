package me.golentrio.classic

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory
import me.goldentrio.source.standard.file

fun main() = Contest({
    directory("CCC2023Autumn/classic/lvl1") {
        outExtension = "validator"
        expected("level1_example.in", "level1_example.validator")
    }
}) {
    val size = readInt(endOfLine = true)

    val map = mutableListOf<MutableList<Type>>()
    for (i in 0..<size) {
        map.add(mutableListOf(*readWholeLine().map { Type.byChar(it) }.toTypedArray()))
    }

    readLine()

    while (hasNextLine()) {
        val (x, y) = readWholeLine().split(",").map { it.toInt() }
        writeValue(map[y][x].char.toString(), lineBreak = true)
    }
}