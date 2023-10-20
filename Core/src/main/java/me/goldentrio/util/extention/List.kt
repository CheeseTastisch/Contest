package me.goldentrio.util.extention

fun <T> Iterable<T>.groupByCompare(compare: (T, T) -> Boolean): Map<T, List<T>> {
    val groups = mutableMapOf<T, MutableList<T>>()

    for (item in this) {
        var found = false

        for (group in groups) {
            if (compare(item, group.key)) {
                group.value.add(item)
                found = true
                break
            }
        }

        if (!found) {
            groups[item] = mutableListOf(item)
        }
    }

    return groups
}