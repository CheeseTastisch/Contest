package me.aoc.day8

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string
import kotlin.math.exp

fun main() = Task({
    string("""
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent()) {
        expected = "34"
    }

    inferFile("day8.in") {
        outExtension = "part2"
    }
}){
    val antennaMap = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    val input = readAllValues()
    val height = input.indices
    val width = 0..<input[0].length

    input
        .flatMapIndexed { y, line -> line.mapIndexed { x, frequency -> (x to y) to frequency } }
        .filter { (_, frequency) -> frequency != '.' }
        .map { (cords, frequency) -> cords to antennaMap.getOrPut(frequency) { mutableListOf() } }
        .forEach { (cords, list) -> list.add(cords) }

    val antinodes = mutableSetOf<Pair<Int, Int>>()
    antennaMap.forEach { (_, antennas) ->
        antennas.forEachIndexed { indexA, coordsA ->
            antennas.forEachIndexed inner@{ indexB, coordsB ->
                if (indexB <= indexA) return@inner

                val (xA, yA) = coordsA
                val (xB, yB) = coordsB

                val deltaX = xB - xA
                val deltaY = yB - yA

                var x = xB
                var y = yB
                while (true) {
                    if (x !in width || y !in height) break
                    antinodes.add(x to y)

                    y += deltaY
                    x += deltaX
                }

                x = xA
                y = yA
                while (true) {
                    if (x !in width || y !in height) break
                    antinodes.add(x to y)

                    y -= deltaY
                    x -= deltaX
                }
            }
        }
    }

    writeValue(antinodes.size)
}