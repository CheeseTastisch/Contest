package me.goldentrio.school

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file
import kotlin.math.max
import kotlin.time.measureTime

fun main() = Task({
//    file("CCC2024Spring/school/lvl4/level4_example.in") {
//        expected("CCC2024Spring/school/lvl4/level4_example.out")
//    }
    directory("CCC2024Spring/school/lvl4") {
        expected("level4_example.in", "level4_example.out")
    }
}) {
    readValues()
    readValues()
    readValues()

    while (hasNextLine()) {
        val coins = readInts()

        val biggestCoin = coins.max()
        val biggestCoinAmount = biggestCoin * 3

        val amounts = readInts()

        val cache = mutableMapOf<Int, List<Int>>()

        for (amount in amounts) {
            if (biggestCoinAmount > amount) {
                writeValue(getCoins(coins, amount)
                    .groupBy { it }.map { "${it.value.size}x${it.key}" }, lineBreak = true
                )
            } else {
                val bigAmount = amount - biggestCoinAmount
                val bigCoins = bigAmount / biggestCoin

                val restAmount = amount - bigCoins * biggestCoin
                println("$restAmount $amount")

                val usedCoins = if (restAmount in cache) cache[restAmount]!!
                else getCoins(coins, restAmount).also { cache[restAmount] = it }

                writeValue(usedCoins
                    .toMutableList()
                    .also { it.addAll(List(bigCoins) { biggestCoin }) }
                    .groupBy { it }.map { "${it.value.size}x${it.key}" }, lineBreak = true
                )
            }
        }
    }

    println("yes")
}


private fun getCoins(coins: List<Int>, amount: Int): List<Int> {
    val dp = Array(amount + 1) { Int.MAX_VALUE }
    val lastCoin = IntArray(amount + 1) { -1 }
    dp[0] = 0

    for (coin in coins) {
        for (i in coin..amount) {
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