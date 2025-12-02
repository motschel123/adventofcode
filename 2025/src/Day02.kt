import java.io.File
import kotlin.math.abs

fun main() {
    println("Day 2!")

    val input = File("resources/day02.txt").readText().trim()

    val IDs = input.split(',')
        .flatMap {
            val startEnd = it.split('-').map { it.toLong() }
            LongRange( startEnd[0], startEnd[1] )
        }
        .map { it.toString() }

    val sum1 = IDs.filter { Regex("^(?<digits>\\d+)(\\k<digits>)$").matches(it) }.sumOf { it.toLong() }
    println("Part 1: $sum1")

    val sum2 = IDs.filter { Regex("^(?<digits>\\d+)(\\k<digits>)+$").matches(it) }.sumOf { it.toLong() }
    println("Part 2: $sum2")
}
