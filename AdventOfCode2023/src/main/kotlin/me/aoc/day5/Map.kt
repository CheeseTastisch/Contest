package me.aoc.day5

data class Map(
    val from: String,
    val to: String,
    val mappingData: MutableList<MappingData>
) {

    operator fun get(source: Long) = mappingData.firstOrNull { source in it }?.get(source) ?: source

}