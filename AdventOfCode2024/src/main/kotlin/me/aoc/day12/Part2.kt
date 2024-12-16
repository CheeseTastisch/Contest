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
        expected = "80"
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
        expected = "436"
    }

    string("""
        EEEEE
        EXXXX
        EEEEE
        EXXXX
        EEEEE
    """.trimIndent()) {
        expected = "236"
    }

    string("""
        AAAAAA
        AAABBA
        AAABBA
        ABBAAA
        ABBAAA
        AAAAAA
    """.trimIndent()) {
        expected = "368"
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
        expected = "1206"
    }

    inferFile("day11.in") {
        outExtension = "part2"
    }
}) {
    val map = readAllValues().map { it.toCharArray().toList() }

    val notSearched = map.mapIndexed { y, row -> List(row.size) { x -> x to y } }.flatten().toMutableSet()
    val regions = mutableListOf<Pair<Set<Pair<Int, Int>>, List<Pair<Int, Int>>>>()

    while (notSearched.isNotEmpty()) {
        val firstNotSearched = notSearched.first()
        regions.add(dfs(map, firstNotSearched, notSearched))
    }

    writeValue(regions.sumOf { (region, edges) -> region.size * findStraightLines(edges) })
}

private val straightDeltas = listOf(
    listOf(0 to 1, 0 to -1),
    listOf(1 to 0, -1 to 0),
)

private fun findStraightLines(edges: List<Pair<Int, Int>>): Int {
    val mutableEdges = edges.toMutableList()
    var counter = 0

    while (mutableEdges.isNotEmpty()) {
        val coordinates = mutableEdges.removeFirst()
        val preferred = findPreferredDirection(mutableEdges, coordinates)
        for (delta in preferred) {
            removeForStraightLine(mutableEdges, coordinates, delta)
        }
        counter++
    }

    return counter
}

private fun findPreferredDirection(edges: List<Pair<Int, Int>>, coordinates: Pair<Int, Int>): List<Pair<Int, Int>> {
    val (x, y) = coordinates

    for (delta in straightDeltas) {
        val (dx1, dy1) = delta[0]
        val (dx2, dy2) = delta[1]

        if (x + dx1 to y + dy1 in edges || x + dx2 to y + dy2 in edges) return delta
    }

    return emptyList()
}

private fun removeForStraightLine(edges: MutableList<Pair<Int, Int>>, coordinates: Pair<Int, Int>, delta: Pair<Int, Int>) {
    val (x, y) = coordinates
    val (dx, dy) = delta

    var nx = x + dx
    var ny = y + dy

    while (nx to ny in edges) {
        // ensure that the edge is only removed once
        val index = edges.indexOf(nx to ny)
        edges.removeAt(index)

        nx += dx
        ny += dy
    }
}