package org.example

import java.io.File
import kotlin.math.absoluteValue


fun main() {
    println("Day 2!")
    // Read input
    // Convert each line to a List<Int>
    val inputData: List<List<Int>> = File("src/main/resources/day02.txt").readLines()
        .map{ reportStr: String ->
            reportStr.split(" ").map { it.toInt() }
        }

    println("Part One:")
    // 1. Calculate the differences
    //      eg. 19 21 24 27 24 -> 2 3 3 -3
    // 2. Validate the differences:
    //      - all pos. or all neg.
    //      - all abs values 0 < x <= 3
    val validDiffs = inputData.map(::calcDiffs).map(::validateDiffs)
    // 3. Count all true's (safe levels)
    println(validDiffs.count{it.all{b -> b}})


    println("Part Two:")
    // Valid diffs (List<List<Boolean>>) as basis
    val result2 = validDiffs.mapIndexed{ idx, levelDiffs ->
        // 1. all fully true levels are true (same as Part 1)
        if (levelDiffs.all { it })
            true
        // 2. in all unsafe levels, try again for each one number being left out
        else {
            for (i in 0 until validDiffs.size+1) {
                val newData = inputData[idx].filterIndexed { index, _ -> index != i }
                if (validateDiffs(calcDiffs(newData)).all { it })
                    return@mapIndexed true
            }
            return@mapIndexed false
        }
    }
    println(result2.count{it})
}

fun calcDiffs(list: List<Int>): List<Int> {
    return list.subList(0, list.size-1).mapIndexed { idx, elem -> list[idx + 1] - elem}
}

fun validateDiffs(list: List<Int>): List<Boolean> {
    return list.map { elem ->
        elem.absoluteValue <= 3
                &&
                (if (list[0] > 0)
                    elem > 0
                else
                    elem < 0)
    }
}