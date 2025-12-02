package org.example

import java.io.File

fun main() {
    val input = File("./src/main/resources/day10.txt").readLines()
        .map {
            it
                .toCharArray()
                .map { c ->
                    if (c == '.')
                        -1
                    else
                        c.digitToInt()
                }.toTypedArray()
        }.toTypedArray()



    val trailheads: MutableList<Pair<Int, Int>> = mutableListOf()
    input.forEachIndexed{ i, row ->
        row.forEachIndexed { j, col ->
            if (col == 0)
                trailheads.add(Pair(i, j))
        }
    }

    fun walkTrail1(pos: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val currVal = input[pos.first][pos.second]
        if (currVal == 9)
            return hashSetOf(pos)

        return listOf(
            Pair(pos.first - 1, pos.second),
            Pair(pos.first + 1, pos.second),
            Pair(pos.first, pos.second - 1),
            Pair(pos.first, pos.second + 1),
        )
        .filter { input.getOrNull(it.first)?.getOrNull(it.second) == currVal + 1 }
        .flatMap { walkTrail1(it) }
        .toSet()
    }

    val result1 = trailheads
        .map { walkTrail1(it) }.sumOf { it.size }
    println("Part One: $result1")



    fun walkTrail2(pos: Pair<Int, Int>): Int {
        val currVal = input[pos.first][pos.second]
        if (currVal == 9)
            return 1

        return listOf(
            Pair(pos.first - 1, pos.second),
            Pair(pos.first + 1, pos.second),
            Pair(pos.first, pos.second - 1),
            Pair(pos.first, pos.second + 1),
        )
        .filter { input.getOrNull(it.first)?.getOrNull(it.second) == currVal + 1 }
        .sumOf { walkTrail2(it) }
    }

    val result = trailheads.sumOf { walkTrail2(it) }


    println("Part One: $result")
}

