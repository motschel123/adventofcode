import java.io.File
import kotlin.time.measureTime

fun main() {
    fun input(): Array<Array<Int>> {
        return File("resources/input04.txt").readLines()
            .map { it.map { c -> if (c == '.') 0 else 1 }.toTypedArray() }.toTypedArray()
    }

    fun part1(input: Array<Array<Int>>): String {
        var forkliftAble = 0
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (input[y][x] == 1) {
                    var surroundingElems = -1
                    for (j in -1..1)
                        for (i in -1..1)
                            surroundingElems += input
                                .getOrElse(y + j, { arrayOf(0) })
                                .getOrElse(x + i, { 0 })
                    if (surroundingElems < 4 ) {
                        forkliftAble++
                    }
                }
            }
        }
        return forkliftAble.toString()
    }

    fun part2(input: Array<Array<Int>>): String {
        val nextInput = input.copyOf()
        var totalForkliftAble = 0
        var forkliftAble: Int
        do {
            forkliftAble = 0
            val currInput = nextInput.copyOf()
            for (y in currInput.indices) {
                for (x in currInput[y].indices) {
                    if (currInput[y][x] == 1) {
                        var surroundingElems = -1
                        for (j in -1..1)
                            for (i in -1..1)
                                surroundingElems += currInput
                                    .getOrElse(y + j, { arrayOf(0) })
                                    .getOrElse(x + i, { 0 })
                        if (surroundingElems < 4) {
                            forkliftAble++
                            nextInput[y][x] = 0
                        }
                    }
                }
            }
            totalForkliftAble += forkliftAble
        } while (forkliftAble > 0)
        return totalForkliftAble.toString()
    }

    println("Day 04!")
    val input = input()

    val result1: String
    println("Part 1 Timing: " + measureTime {
        result1 = part1(input)
    })
    println("Part 1 Result: " + result1)

    val result2: String
    println("Part 2 Timing: " + measureTime {
        result2 = part2(input)
    })
    println("Part 2 Result: " + result2)
}