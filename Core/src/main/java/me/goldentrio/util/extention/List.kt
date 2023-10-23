package me.goldentrio.util.extention

/**
 * Groups the elements of the original collection.
 * If two elements that are parsed to the [compare] function are equal, they will be grouped together.
 *
 * The [compare] function should be able to compare two elements of the collection,
 * with the following rules:
 * - If `a == b`, then `compare(a, b) == true`.
 * - If `compare(a, b) == true`, then `compare(b, a) == true`.
 * - If `compare(a, b) == false`, then `compare(b, a) == false`.
 * - If `compare(a, b) == true` and `compare(b, c) == true`, then `compare(a, c) == true`.
 */
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