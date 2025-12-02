package org.example

import java.io.File
import java.math.BigInteger
import java.math.MathContext


fun main() {
    println("Part Two: ")
    val result = File("src/main/resources/Day13.txt")
        .readLines()
        .asSequence()
        .chunked(4)
        .map { it.filter(String::isNotEmpty) }
        .map { lines ->
            val regexRes = Regex("\\d+").findAll(lines.joinToString("")).toList().map { it.value.toBigDecimal()}
            var (ax, ay, bx, by, px) = regexRes
            var py = regexRes[5]

            px += 10000000000000.toBigDecimal()
            py += 10000000000000.toBigDecimal()

            val ca = (px * by - py * bx) / (ax * by - bx * ay)
            val cb = (px - ax * ca) / bx

            if (ca % 1.toBigDecimal() == 0.toBigDecimal() && (cb % 1.toBigDecimal() == 0.toBigDecimal()) &&
                ca * ax + cb * bx == px &&
                ca * ay + cb * by == py)
                Pair(ca, cb)
            else
                Pair(0.toBigDecimal(), 0.toBigDecimal())
        }
        .map {
            println(it)
            it
        }
        .sumOf {
            it.first * 3.toBigDecimal() + it.second
        }
    println(result)
}