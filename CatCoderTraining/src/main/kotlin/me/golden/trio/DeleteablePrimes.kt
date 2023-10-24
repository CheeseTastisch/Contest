package me.golden.trio

import me.goldentrio.Contest
import me.goldentrio.source.standard.string

fun main() = Contest({
    string("4567") {
        expected = "3"
    }

    string("4125673")
    string("41256793")
    string("337424981")
    string("537430451")
    string("200899998")
    string("537499093")
    string("2147483059")
    string("410256793")
    string("567629137")
    string("46216567629137")
}) {
    val number = readValue().toLong()
    writeInt(getDeletablePrimes(number))
}

private fun getDeletablePrimes(number: Long): Int {
    if (!isPrime(number)) return 0
    if (number < 10) return 1

    var deletablePrimes = 0
    val numberString = number.toString()

    for (i in numberString.indices) {
        val newNumber = numberString.removeRange(i, i + 1).toLong()
        deletablePrimes += getDeletablePrimes(newNumber)
    }

    return deletablePrimes
}

private fun isPrime(number: Long): Boolean {
    if (number <= 1) return false
    if (number <= 3) return true

    if (number % 2 == 0L || number % 3 == 0L) return false

    var i = 5L
    while (i * i <= number) {
        if (number % i == 0L || number % (i + 2) == 0L) return false
        i += 6
    }

    return true
}