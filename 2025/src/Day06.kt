import java.io.File
import kotlin.time.measureTime

fun main() {
    fun input(): List<String> {
        return File("resources/input06.txt").readLines()
    }

    fun part1(input: List<String>): String {
        val numbers: Array<Array<Long>> = Array(input.first().trim().split(Regex("\\s+")).size) { Array(input.size - 1) { 0 } }
        input.take(input.size - 1)
            .forEachIndexed { y, line ->
                line.trim().split(Regex("\\s+")).forEachIndexed { x, str -> numbers[x][y] = str.toLong() }
            }
        val operators = input.last().trim().split(Regex("\\s+")).map { it.trim() }.toTypedArray()

        return numbers.zip(operators).sumOf { (list, op) ->
            when (op) {
                "*" -> list.reduce { acc, i -> acc * i }
                "+" -> list.reduce { acc, i -> acc + i }
                else -> 0
            }
        }.toString()
    }

    fun part2(input: List<String>): String {
        val chars: Array<CharArray> = input
            .map { it.toCharArray() }.dropLast(1).toTypedArray()
        var numbers: Array<String> = Array(chars[0].size) { "" }
        for (y in chars.indices) {
            for (x in chars[y].indices) {
                numbers[x] = numbers[x].plus(chars[y][x])
            }
        }
        numbers = numbers.map { it.trim() }.plus("").toTypedArray()

        var currNums = mutableListOf<Long>()
        var idx = 0
        val operators = input.last().split(Regex("\\s+"))
        return numbers.fold(0L) { result, num ->
            if (num.isEmpty()) {
                val ret = when (operators[idx]) {
                    "*" -> currNums.reduce { acc, i -> acc * i }
                    "+" -> currNums.reduce { acc, i -> acc + i }
                    else -> result
                }
                currNums = mutableListOf()
                idx++
                result + ret
            } else {
                currNums.add(num.toLong())
                result
            }
        }.toString()
    }

    println("Day 06!")
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