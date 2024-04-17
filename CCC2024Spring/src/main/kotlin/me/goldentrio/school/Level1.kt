package me.goldentrio.school

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
    file("CCC2024Spring/school/lvl1/level1_example.in") {
        expected("CCC2024Spring/school/lvl1/level1_example.out")
    }
//    directory("CCC2024Spring/school/lvl1") {
//        expected("level1_example.in", "level1_example.out")
//    }
}) {

}