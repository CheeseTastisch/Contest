package me.golden.trio.genetic.drift

import me.goldentrio.Contest
import me.goldentrio.source.standard.string
import kotlin.math.abs

fun main() = Contest({
    string("8 0 3 1 6 5 -2 4 7 1 2 -2 5") {
        expected = "2"
    }

    string("8 0 3 1 6 5 -2 4 7 3 1 -2 5") {
        expected = "4"
    }

    string("8 0 3 1 6 5 -2 4 7 1 2 -2 5")
    string("8 0 3 1 6 5 -2 4 7 3 1 -2 5")
    string("193 125 133 134 135 136 -52 -51 -50 -49 -48 -47 -46 -45 66 67 68 69 70 71 -38 -37 -36 -35 -34 -33 -32 -31 -30 -29 -132 -131 -130 -193 -192 -191 -190 -189 -188 -187 -186 -185 -184 -183 -182 -181 -180 -179 -178 -177 -176 -175 -174 -173 -172 -171 -170 -169 -77 -76 -75 -74 -73 -72 18 19 20 21 22 23 24 25 26 27 28 -164 -163 -65 -64 -63 -62 -61 -60 -59 -58 -57 -56 -55 -54 -53 39 40 41 42 43 44 159 160 161 162 -17 -16 -15 -14 -13 -12 -11 -10 -9 -8 -7 -6 -5 -4 -3 -2 -1 -168 -167 -166 -165 126 127 128 129 86 87 88 89 90 91 92 93 94 95 96 -124 -123 -122 -121 -120 -119 -118 -117 -116 -115 -114 -113 -112 -111 -110 -109 -108 -107 -106 -105 -104 -103 -102 -101 -100 -99 -98 -97 153 154 155 156 157 158 -148 -147 -146 -145 -144 -143 -142 -141 -140 -139 -138 -137 -85 -84 -83 -82 -81 -80 -79 -78 -152 -151 -150 -149 -45 12 44 94")
}) {
    val amount = readInt()

    val input = mutableListOf<Int>()
    for (i in 0..<amount) input.add(readInt())

    val (startValue, startIndex, endValue, endIndex) = readInts()

    val output = input.toMutableList()

    val range = if (startValue + endValue == 1) startIndex..<endIndex else startIndex + 1..endIndex

    val fix = if (startValue + endValue == 1) 1 else -1
    for (i in range) output[i] = input[endIndex - i + startIndex - fix] * -1

    val orientedPairs = mutableListOf<Pair<Int, Int>>()

    for (i in output.indices) {
        for (j in i + 1..<output.size) {
            val valueI = output[i]
            val valueJ = output[j]

            if (valueI == 0 && valueJ == 1) continue
            if (valueI == 1 && valueJ == 0) continue

            if (abs(abs(valueI) - abs(valueJ)) == 1 && valueI * valueJ <= 0)
                orientedPairs.add(output[i] to output[j])
        }
    }

    writeInt(orientedPairs.size)
}