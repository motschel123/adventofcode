package org.example

import java.io.File
import java.math.BigInteger
import kotlin.math.log10

fun main() {
    val input = File("src/main/resources/day11.txt")
        .readText()
        .trim()
        .split(" ")

    fun updateStone(stone: String): List<String> {
        return when {
            stone == "0" -> listOf("1")
            stone.length % 2 == 0 -> listOf(
                    stone.substring(0, stone.length/2),
                    stone.substring(stone.length/2).toLong().toString()
            )
            else -> listOf((stone.toLong() * 2024).toString())
        }
    }

    fun calculateStone(stone: String, depth: Int, cache: MutableMap<Pair<String, Int>, ULong>): ULong {
        if (depth == 0)
            return 1u

        cache[stone to depth]?.let { return it }
        if (stone.startsWith("-"))
            println(stone)
        return when {
            stone == "0" -> {
                val result = calculateStone("1", depth - 1, cache)
                cache[Pair(stone, depth)] = result
                result
            }
            stone.length % 2 == 0 -> {
                val nextStones = listOf(
                    stone.substring(0, stone.length / 2),
                    stone.substring(stone.length / 2).toLong().toString()
                )

                val result: ULong = calculateStone(nextStones[0], depth - 1, cache) +
                        calculateStone(nextStones[1], depth - 1, cache)
                cache[stone to depth] = result
                result
            }
            else -> {
                val result = calculateStone((stone.toULong() * 2024u).toString(), depth - 1, cache)
                cache[stone to depth] = result
                result
            }

        }
    }

    var result1 = input
    for (i in 1..25)
        result1 = result1.flatMap(::updateStone)
    println("Part One ${result1.count()}")

    val globCache1 = mutableMapOf<Pair<String, Int>, ULong>()
    println(input.sumOf { calculateStone(it, 25, globCache1)})


    val globCache = mutableMapOf<Pair<String, Int>, ULong>()


    println("Part Two: ")
    println(input.sumOf { calculateStone(it, 75, globCache)})
}