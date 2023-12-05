package me.aoc.day5

data class MappingData(
    val sourceStart: Long,
    val destinationStart: Long,
    val range: Long,
) {

    operator fun contains(source: Long) = source >= sourceStart && source < sourceStart + range

    operator fun get(source: Long): Long {
        if (source !in this) throw IllegalArgumentException("Source $source is not in range $this")
        return destinationStart + source - sourceStart
    }

}