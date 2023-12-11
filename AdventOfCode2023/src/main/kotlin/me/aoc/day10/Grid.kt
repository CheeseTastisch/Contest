package me.aoc.day10

data class Grid(
    val list: List<Part>,
) {

    val start = list.first { it.type == PartType.START }

    val loop by lazy {
        for (direction in Direction.entries) {
            val loop = mutableListOf<Part>()

            var current = inDirection(start, direction) ?: continue
            if (direction.inverse.let { it != current.type.first && it != current.type.second }) continue
            var from = direction.inverse

            while (current.type.let { it != PartType.GROUND && it != PartType.START }) {
                loop.add(current)

                val nextDirection = next(current, from) ?: break

                from = nextDirection.inverse
                current = inDirection(current, nextDirection) ?: break
            }

            if (current.type == PartType.START) return@lazy loop
        }

        return@lazy emptyList()
    }

    val distanceLoop by lazy {
        for (direction in Direction.entries) {
            val loop = mutableMapOf<Part, Int>()

            var current = inDirection(start, direction) ?: continue
            if (direction.inverse.let { it != current.type.first && it != current.type.second }) continue
            var from = direction.inverse

            var distance = 1

            while (current.type.let { it != PartType.GROUND && it != PartType.START }) {
                loop[current] = distance++

                val nextDirection = next(current, from) ?: break

                from = nextDirection.inverse
                current = inDirection(current, nextDirection) ?: break
            }

            if (current.type == PartType.START) {
                current = inDirection(current, from) ?: continue
                from = from.inverse
                distance = 1

                while (current.type.let { it != PartType.GROUND && it != PartType.START }) {
                    if ((loop[current] ?: Int.MAX_VALUE) > distance) loop[current] = distance++
                    else distance++

                    val nextDirection = next(current, from) ?: break

                    from = nextDirection.inverse
                    current = inDirection(current, nextDirection) ?: break
                }

                return@lazy loop
            }
        }

        return@lazy emptyMap()
    }

    fun at(x: Int, y: Int) =
        list.firstOrNull { it.x == x && it.y == y }

    fun inDirection(part: Part, direction: Direction) = at(
        part.x + direction.delta.first,
        part.y + direction.delta.second
    )

    fun next(part: Part, from: Direction) = when (from) {
        part.type.first -> part.type.second!!
        part.type.second -> part.type.first!!
        else -> null
    }

    override fun toString() = buildString {
        var x = 0
        var y = 0

        while (true) {
            val part = at(x, y)
            if (part == null) {
                x = 0
                y++

                if (at(x, y) == null) break
                else {
                    appendLine()
                    continue
                }
            }

            if (part in loop) append(part.type)
            else if (part.type == PartType.GROUND) append('.')
            else append(' ')

            x++
        }
    }

}