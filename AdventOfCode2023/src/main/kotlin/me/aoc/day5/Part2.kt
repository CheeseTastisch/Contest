package me.aoc.day5

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent()) {
        expected = "46"
    }
    file("AdventOfCode2023/in/day5/part2.in")
}){
    val maps = mutableMapOf<String, Map>()
    var currentMap: Map? = null

    readValue()
    val seeds = readLongs().chunked(2).map { SeedRange(it[0], it[1]) }
    readValue(endOfLine = true)

    while (hasNextLine()) {
        if (currentMap == null) {
            val (from, _, to) = readValue(endOfLine = true).split("-")
            currentMap = Map(from, to, mutableListOf())
            continue
        }

        if (readValue(peek = true).isBlank()) {
            readValue(endOfLine = true)
            maps[currentMap.from] = currentMap
            currentMap = null
            continue
        }

        val (destinationStart, sourceStart, range) = readLongs()
        currentMap.mappingData.add(MappingData(sourceStart, destinationStart, range))
    }
    maps[currentMap!!.from] = currentMap

    var currentSeeds = seeds
    var currentCategory = "seed"
    while (currentCategory != "location") {
        val useMap = maps[currentCategory]!!
        currentSeeds = currentSeeds.flatMap { seed -> seed.map(useMap) }
        currentCategory = useMap.to
    }

    writeValue(currentSeeds.minOf { it.start })
}