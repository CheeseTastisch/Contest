package me.golden.trio.ccc2023spring.school

import me.task.Task
import me.task.source.standard.directory

fun main() = Task({
    directory("CatCoderTraining/ccc2023spring/school/level1/") {
        expected("level1_example.in", "level1_example.out")
    }
}) {
    var cells = 0
    while (hasNextLine()) cells += readValue(endOfLine = true).count { it == 'O' }

    writeValue(cells)
}