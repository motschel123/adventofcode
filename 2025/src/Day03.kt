import java.io.File
import kotlin.time.measureTime

fun main() {
    fun input(): List<String> {
        return File("2025/resources/input03.txt").readLines()
    }

    fun part1(input: List<String>): String {
        return input.fold(0) { acc, it ->
            val firstDigit = it
                .subSequence(0, it.length - 1) // exclude last for the second digit
                .withIndex()
                .maxBy { (_, c) -> c.digitToInt()}
            val secondDigit = it.subSequence(firstDigit.index+1, it.length)
                .maxBy { it.digitToInt() }
            acc + "${firstDigit.value}$secondDigit".toInt()
        }.toString()
    }

    fun part2(input: List<String>): String {
        var digitsLeft: Int
        var digits: String
        var currIdx: Int
        return input.fold(0L) { acc, it ->
            digitsLeft = 11
            digits = ""
            currIdx = 0

            while (digits.length < 12) {
                val currDigit = it
                    .subSequence(currIdx, it.length - digitsLeft) // always keep enough digits to make len 12
                    .withIndex()
                    .maxBy { (_, c) -> c.digitToInt()}

                digits += currDigit.value
                digitsLeft--
                currIdx += currDigit.index + 1
            }
            acc + digits.toLong()
        }.toString()
    }

    println("Day 03!")
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