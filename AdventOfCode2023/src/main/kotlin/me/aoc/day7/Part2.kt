package me.aoc.day7

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()) {
        expected = "5905"
    }
    file("AdventOfCode2023/in/day7/part2.in")
}) {
    data class Hand(
        private val cards: List<CardValue>,
        val value: Int,
    ) : Comparable<Hand> {

        val jokers = cards.count { it == CardValue.JOKER }
        val nonJokers = cards.filter { it != CardValue.JOKER }

        val isFiveOfAKind: Boolean
            get() = nonJokers.groupBy { it }.values.any { (it.size + jokers) == 5 } || jokers == 5

        val isFourOfAKind: Boolean
            get() = nonJokers.groupBy { it }.values.any { (it.size + jokers) == 4 } || jokers == 4

        val isFullHouse: Boolean
            get() {
                val groups = nonJokers.groupBy { it }.values

                val hasNaturalThree = groups.any { it.size == 3 }
                val hasNaturalPair = groups.any { it.size == 2 }

                return when {
                    hasNaturalThree && hasNaturalPair -> true
                    hasNaturalThree -> jokers >= 1
                    hasNaturalPair -> {
                        if (groups.count { it.size == 2 } == 2) return jokers >= 1
                        return jokers >= 2
                    }
                    else -> jokers >= 3
                }
            }

        val isThreeOfAKind: Boolean
            get() = nonJokers.groupBy { it }.values.any { (it.size + jokers) == 3 } || jokers == 3

        val isTwoPairs: Boolean
            get() {
                return when (nonJokers.groupBy { it }.values.count { it.size == 2  }) {
                    2 -> true
                    1 -> jokers >= 1
                    else -> jokers >= 2
                }
            }

        val isOnePair: Boolean
            get() = nonJokers.groupBy { it }.values.any { (it.size + jokers) == 2 } || jokers == 2

        override fun compareTo(other: Hand): Int {
            if (isFiveOfAKind && !other.isFiveOfAKind) return 1
            if (!isFiveOfAKind && other.isFiveOfAKind) return -1
            if (isFiveOfAKind && other.isFiveOfAKind) return compareByCardValue(other)

            if (isFourOfAKind && !other.isFourOfAKind) return 1
            if (!isFourOfAKind && other.isFourOfAKind) return -1
            if (isFourOfAKind && other.isFourOfAKind) return compareByCardValue(other)

            if (isFullHouse && !other.isFullHouse) return 1
            if (!isFullHouse && other.isFullHouse) return -1
            if (isFullHouse && other.isFullHouse) return compareByCardValue(other)

            if (isThreeOfAKind && !other.isThreeOfAKind) return 1
            if (!isThreeOfAKind && other.isThreeOfAKind) return -1
            if (isThreeOfAKind && other.isThreeOfAKind) return compareByCardValue(other)

            if (isTwoPairs && !other.isTwoPairs) return 1
            if (!isTwoPairs && other.isTwoPairs) return -1
            if (isTwoPairs && other.isTwoPairs) return compareByCardValue(other)

            if (isOnePair && !other.isOnePair) return 1
            if (!isOnePair && other.isOnePair) return -1
            if (isOnePair && other.isOnePair) return compareByCardValue(other)

            return compareByCardValue(other)
        }

        private fun compareByCardValue(other: Hand): Int {
            for (i in cards.indices) {
                if (cards[i] < other.cards[i]) return 1
                if (cards[i] > other.cards[i]) return -1
            }

            return 0
        }


    }

    val hands = mutableListOf<Hand>()
    while (hasNextLine()) {
        val cards = readValue().map { CardValue.fromIdentifier(it, true) }
        val value = readInt(endOfLine = true)

        hands.add(Hand(cards, value))
    }

    writeValue(hands.sorted().mapIndexed { index, hand -> hand.value * (index + 1) }.sum())
}