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
        expected = "14"
    }

    inferFile("day8.in") {
        outExtension = "part1"
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

                val newY = yB + deltaY
                val newX = xB + deltaX
                if (newX in width && newY in height) {
                    antinodes.add(newX to newY)
                }

                val newY2 = yA - deltaY
                val newX2 = xA - deltaX
                if (newX2 in width && newY2 in height) {
                    antinodes.add(newX2 to newY2)
                }
            }
        }
    }

    writeValue(antinodes.size)
}