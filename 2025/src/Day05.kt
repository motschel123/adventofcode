import java.io.File
import kotlin.time.measureTime

fun main() {
    fun input(): List<String> {
        return File("resources/input05.txt").readLines()
    }

    fun part1(input: List<String>): String {
        val ranges = input
            .takeWhile { it.isNotEmpty() }
            .map { it.split('-').map { it.toLong() } }
            .map { (start, end) -> LongRange(start, end) }
            .sortedBy { it.start }
        val ids = input
            .drop(ranges.size+1)
            .map { it.toLong() }
            .sorted()

        var currRange = 0

        return ids.count { i ->
            while (currRange < ranges.size-1 && i > ranges[currRange].endInclusive) currRange++
            ranges[currRange].contains(i)
        }.toString()
    }

    fun part2(input: List<String>): String {
        val ranges = input
            .takeWhile { it.isNotEmpty() }
            .map { line ->
                line.split('-').map { it.toULong() }
            }
            .map { (start, end) -> ULongRange(start, end) }
            .sortedBy { it.start }

        val resultRanges = mutableListOf<ULongRange>()
        var currRange = ranges.first()
        ranges.drop(1).forEach { range ->
            // println("$range, ${range.endInclusive - range.start + 1UL}")
            if (range.first <= currRange.endInclusive) {
                currRange = currRange.start..maxOf(currRange.endInclusive, range.endInclusive)
            } else {
                resultRanges.add(currRange)
                currRange = range
            }
        }
        resultRanges.add(currRange)
        resultRanges.forEach {
            println("$it, ${it.endInclusive - it.start + 1UL}")
        }

        return resultRanges.sumOf { it.endInclusive - it.start + 1UL }.toString()
    }

    println("Day 05!")
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