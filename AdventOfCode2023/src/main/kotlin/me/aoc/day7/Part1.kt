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
        expected = "6440"
    }
    file("AdventOfCode2023/in/day7/part1.in")
}){
    data class Hand(
        private val cards: List<CardValue>,
        val value: Int,
    ) : Comparable<Hand> {

        private val isFiveOfAKind: Boolean
            get() = cards.groupBy { it }.values.any { it.size == 5 }

        private val isFourOfAKind: Boolean
            get() = cards.groupBy { it }.values.any { it.size == 4 }

        private val isFullHouse: Boolean
            get() = cards.groupBy { it }.values.any { it.size == 3 }
                    && cards.groupBy { it }.values.any { it.size == 2 }

        private val isThreeOfAKind: Boolean
            get() = cards.groupBy { it }.values.any { it.size == 3 }

        private val isTwoPairs: Boolean
            get() = cards.groupBy { it }.values.count { it.size == 2 } == 2

        private val isOnePair: Boolean
            get() = cards.groupBy { it }.values.any { it.size == 2 }

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
        val cards = readValue().map { CardValue.fromIdentifier(it) }
        val value = readInt(endOfLine = true)

        hands.add(Hand(cards, value))
    }

    writeValue(hands.sorted().mapIndexed { index, hand -> hand.value * (index + 1) }.sum())
}