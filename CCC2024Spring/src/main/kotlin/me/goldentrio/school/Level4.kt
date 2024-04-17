package me.goldentrio.school

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
    file("CCC2024Spring/school/lvl4/level4_example.in") {
        expected("CCC2024Spring/school/lvl4/level4_example.out")
    }
//    directory("CCC2024Spring/school/lvl4") {
//        expected("level4_example.in", "level4_example.out")
//    }
}) {

}