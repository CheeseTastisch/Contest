package me.goldenduo

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
//    file("CCC2024Autumn/in/lvl3/level3_example.in") {
//        expected("CCC2024Autumn/in/lvl3/level3_example.out")
//    }
    directory("CCC2024Autumn/in/lvl3") {
        expected("level3_example.in", "level3_example.out")
    }
}){
    readValues()

    while (hasNextLine()) {
        val (x, y, _) = readInts()

        val ids = Ids()
        val verticalDesks = x % 3

        val deskMap = (1..y).map { _ ->
            (1..x).map { currentX ->
                if(currentX == x) {
                    if (verticalDesks == 0) {
                        ids.getId("primary")
                    } else {
                        ids.getId("last")
                    }
                } else if (currentX == x-1) {
                    if (verticalDesks == 2) {
                        ids.getId("secondLast")
                    } else {
                        ids.getId("primary")
                    }
                } else {
                    ids.getId("primary")
                }
            }
        }

        val removeDesks = ids.getIncompleteDesks()
        val biggestIncomplete = removeDesks.maxOrNull() ?: Int.MAX_VALUE

        val actualDesks = deskMap.map { row ->
            row.map {
                if (it in removeDesks) 0
                else if (it > biggestIncomplete) it - verticalDesks
                else it
            }
        }

        actualDesks.forEach {
            writeValue(it, lineBreak = true)
        }

        writeValue(lineBreak = true)
    }
}

private class Ids {

    private var counter = 1

    private val idMap = mutableMapOf<String, Int>()
    private val lengthMap = mutableMapOf<String, Int>()

    fun getId(name: String): Int {
        if (name !in idMap) {
            idMap[name] = counter++
            lengthMap[name] = 1
        } else {
            val currentLength = lengthMap[name]!!
            if (currentLength == 3) {
                idMap[name] = counter++
                lengthMap[name] = 1
            } else {
                lengthMap[name] = currentLength + 1
            }
        }

        return idMap[name]!!
    }

    fun getIncompleteDesks(): List<Int> {
        return lengthMap.filter { (_, v) -> v < 3 }.keys.map { idMap[it]!! }
    }

}