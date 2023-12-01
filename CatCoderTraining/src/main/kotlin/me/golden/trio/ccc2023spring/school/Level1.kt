package me.golden.trio.ccc2023spring.school

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory

fun main() = Contest({
    directory("CatCoderTraining/ccc2023spring/school/level1/") {
        expected("level1_example.in", "level1_example.out")
    }
}) {
    var cells = 0
    while (hasNextLine()) cells += readValue(endOfLine = true).count { it == 'O' }

    writeValue(cells)
}