package me.aoc.day7

interface Entry {

    val name: String
    val size: Int

}

data class Directory(override val name: String, val entries: MutableList<Entry>) : Entry {

    override val size by lazy { entries.sumOf { it.size } }

    val recursiveSubdirectories: List<Directory> by lazy {
        entries.filterIsInstance<Directory>()
            .flatMap { it.recursiveSubdirectories + it }
            .distinct() + this
    }

    fun getDirectory(name: String): Directory? {
        if (name == ".") return this
        if (name.startsWith("/")) return getDirectory(name.drop(1))

        val parts = name.split("/")
        val directory = entries.filterIsInstance<Directory>().firstOrNull { it.name == parts[0] }
        return if (parts.size == 1) directory else directory?.getDirectory(parts.drop(1).joinToString("/"))
    }

    operator fun contains(name: String): Boolean {
        val parts = name.split("/")
        if (parts.size == 1) return entries.any { it.name == parts[0] }
        return getDirectory(parts.dropLast(1).joinToString("/"))?.contains(parts.last()) ?: false
    }

}

data class File(override val name: String, override val size: Int) : Entry