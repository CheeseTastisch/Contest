package me.aoc.day7

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()) {
        expected = "95437"
    }
    file("AdventOfCode2022/in/day7/part1.in")
}) {
    var current = ""
    val root = Directory("/", mutableListOf())

    while (hasNextLine()) {
        if (readValue(peek = true).startsWith("$")) {
            readValue()
            if (readValue() == "cd") {
                val to = readValue()
                current = when(to) {
                    "/" -> "/"
                    ".." -> current.split("/").dropLast(1).joinToString("/")
                    else -> "$current/$to"
                }
            }

            readValues()
            continue
        }

        val currentDirectory = if (current == "/") root else root.getDirectory(current)!!

        val (type, name) = readValues()
        if (name in currentDirectory) continue

        currentDirectory.entries.add(
            if (type == "dir") Directory(name, mutableListOf())
            else File(name, type.toInt())
        )
    }

    writeValue(root.recursiveSubdirectories.filter { it.size < 100000 }.sumOf { it.size })
}