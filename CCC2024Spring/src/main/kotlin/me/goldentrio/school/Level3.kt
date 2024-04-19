package me.goldentrio.school

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
    file("CCC2024Spring/school/lvl3/level3_example.in") {
        expected("CCC2024Spring/school/lvl3/level3_example.out")
    }
//    directory("CCC2024Spring/school/lvl3") {
//        expected("level3_example.in", "level3_example.out")
//    }
}) {
    readValues()
    readValues()

    while (hasNextLine()) {
        val coins = readInts()

        for (amount in 1..100) {
            writeValue(getCoins(coins, amount).groupBy { it }.map { "${it.value.size}x${it.key}" }, lineBreak = true)
        }
    }
}

private fun getCoins(coins: List<Int>, amount: Int): List<Int> {
    val dp = Array(amount + 1) { Int.MAX_VALUE }
    val lastCoin = IntArray(amount + 1) { -1 }
    dp[0] = 0

    for (coin in coins) {
        for (i in coin..<amount + 1) {
            if (dp[i - coin] + 1 < dp[i]) {
                dp[i] = dp[i - coin] + 1
                lastCoin[i] = coin
            }
        }
    }

    if (dp[amount] == Int.MAX_VALUE) return emptyList()

    val result = mutableListOf<Int>()
    var x = amount
    while (x > 0) {
        val coin = lastCoin[x]
        result.add(coin)
        x -= coin
    }

    return result
}