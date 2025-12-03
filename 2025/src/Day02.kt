import java.io.File
import kotlin.time.measureTime

fun main() {
    println("Day 2!")


    val input = File("2025/resources/input02.txt").readText().trim()

    val IDs = input.split(',')
        .flatMap {
            val startEnd = it.split('-').map { it.toLong() }
            LongRange( startEnd[0], startEnd[1] )
        }
        .map { it.toString() }

    val sum1: Long
    println("Time part 1: " + measureTime {
        sum1 = IDs.filter { Regex("^(?<digits>\\d+)(\\k<digits>)$").matches(it) }.sumOf { it.toLong() }
    })
    println("Part 1: $sum1")

    val sum2: Long
    println("Time part 2: " + measureTime {
        sum2 = IDs.filter { Regex("^(?<digits>\\d+)(\\k<digits>)+$").matches(it) }.sumOf { it.toLong() }
    })
    println("Part 2: $sum2")
}
