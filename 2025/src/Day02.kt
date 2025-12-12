import java.io.File
import kotlin.time.measureTime

fun main() {
    fun input(): String {
        return File("resources/input02.txt").readText().trim()
            .split(',')
            .flatMap {
                val startEnd = it.split('-').map { it.toLong() }
                LongRange(startEnd[0], startEnd[1])
            }
            .joinToString("\n") { it.toString() }
    }

    fun part1(input: String): String {
        return Regex("^(?<digits>\\d+)(\\k<digits>)$", RegexOption.MULTILINE).findAll(input).sumOf { it.value.toLong() }.toString()
    }

    fun part2(input: String): String {
        return Regex("^(?<digits>\\d+)(\\k<digits>)+$", RegexOption.MULTILINE).findAll(input).sumOf { it.value.toLong() }.toString()
    }


    println("Day 02!")
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