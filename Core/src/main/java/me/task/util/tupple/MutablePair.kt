package me.task.util.tupple

import java.io.Serializable

/**
 * A mutable pair of two values.
 */
data class MutablePair<A, B>(
    var first: A,
    var second: B
) : Serializable {

    override fun toString(): String = "($first, $second)"

}
