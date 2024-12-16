package me.aoc.day12

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string(
        """
        AAAA
        BBCD
        BBCC
        EEEC
    """.trimIndent()
    ) {
        expected = "140"
    }

    string(
        """
        OOOOO
        OXOXO
        OOOOO
        OXOXO
        OOOOO
    """.trimIndent()
    ) {
        expected = "772"
    }

    string(
        """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.trimIndent()
    ) {
        expected = "1930"
    }

    inferFile("day11.in") {
        outExtension = "part1"
    }
}) {
    val map = readAllValues().map { it.toCharArray().toList() }

    val notSearched = map.mapIndexed { y, row -> List(row.size) { x -> x to y } }.flatten().toMutableSet()
    val regions = mutableListOf<Pair<Set<Pair<Int, Int>>, List<Pair<Int, Int>>>>()

    while (notSearched.isNotEmpty()) {
        val firstNotSearched = notSearched.first()
        regions.add(dfs(map, firstNotSearched, notSearched))
    }

    writeValue(regions.sumOf { (region, edges) -> region.size * edges.size })
}

private val deltas = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)

fun dfs(
    map: List<List<Char>>,
    coordinates: Pair<Int, Int>,
    notSearched: MutableSet<Pair<Int, Int>>,
): Pair<Set<Pair<Int, Int>>, List<Pair<Int, Int>>> {
    if (!notSearched.remove(coordinates)) return emptySet<Pair<Int, Int>>() to emptyList() // already searched

    val (x, y) = coordinates
    val char = map[y][x]

    val result = mutableSetOf(coordinates)
    val edges = mutableListOf<Pair<Int, Int>>()

    for ((dx, dy) in deltas) {
        val (nx, ny) = x + dx to y + dy
        if (map.getOrNull(ny)?.getOrNull(nx) == char) {
            val (newResult, newEdges) = dfs(map, nx to ny, notSearched)
            result.addAll(newResult)
            edges.addAll(newEdges)
        } else {
            edges.add(nx to ny)
        }
    }

    return result to edges
}