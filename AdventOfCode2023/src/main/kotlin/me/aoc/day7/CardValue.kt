package me.aoc.day7

enum class CardValue(val identifier: Char) {

    ACE('A'),
    KING('K'),
    QUEEN('Q'),
    JACK('J'),
    TEN('T'),
    NINE('9'),
    EIGHT('8'),
    SEVEN('7'),
    SIX('6'),
    FIVE('5'),
    FOUR('4'),
    THREE('3'),
    TWO('2'),
    JOKER('J');

    companion object {
        fun fromIdentifier(identifier: Char, jAsJoker: Boolean = false): CardValue {
            if (identifier == 'J' && jAsJoker) return JOKER
            return entries.first { it.identifier == identifier }
        }
    }

}