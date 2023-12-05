package me.aoc.day5

data class SeedRange(
    val start: Long,
    val range: Long,
) {

    fun map(map: Map): List<SeedRange> {
        val result = mutableListOf<SeedRange>()

        var currentStart = start
        var missingRange = range

        while (missingRange > 0) {
            val usedData = map.mappingData.firstOrNull { currentStart in it }

            if (usedData == null) {
                val nextData = map.mappingData.filter { it.sourceStart > currentStart }.minByOrNull { it.sourceStart }
                if (nextData == null) {
                    result.add(SeedRange(currentStart, missingRange))
                    break
                }

                val possibleRange = nextData.sourceStart - currentStart
                val usedRange = minOf(possibleRange, missingRange)

                result.add(SeedRange(currentStart, usedRange))

                currentStart += usedRange
                missingRange -= usedRange

                continue
            }

            val possibleRange = usedData.range - (currentStart - usedData.sourceStart)
            val usedRange = minOf(possibleRange, missingRange)

            result.add(SeedRange(map[currentStart], usedRange))

            currentStart += usedRange
            missingRange -= usedRange
        }

        return result
    }

}