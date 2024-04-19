package me.goldentrio.school

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
//    file("CCC2024Spring/school/lvl2/level2_example.in") {
//        expected("CCC2024Spring/school/lvl2/level2_example.out")
//    }
    directory("CCC2024Spring/school/lvl2") {
        expected("level2_example.in", "level2_example.out")
    }
}) {
    readValues()
    readValues()
    readValues()

    while (hasNextLine()) {
        val coins = readInts()
        val amounts = readInts()

        for(amount in amounts) {
            val (coin1, coin2) = getCoins(coins, amount)
            writeValue(coin1, coin2, lineBreak = true)
        }
    }
}

private fun getCoins(coins: List<Int>, amount: Int): Pair<Int, Int> {
    val missing = mutableMapOf<Int, Int>()

    for (coin in coins) {
        if (coin in missing) return missing[coin]!! to coin
        if (coin * 2 == amount) return coin to coin

        missing[amount - coin] = coin
    }

    throw IllegalArgumentException("No coins found")
}