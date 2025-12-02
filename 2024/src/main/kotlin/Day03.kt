package org.example

import java.io.File
import kotlin.math.absoluteValue
import kotlin.streams.asStream


fun main() {
    println("Day 3!")
    // Read input
    // Convert each line to a List<Int>
    val input: String = File("src/main/resources/day03.txt").readLines().joinToString()


    println("Part One:")
    // matches group 1 includes both numbers and ','
    // eg. '840,120'
    val result1 = Regex("(?:mul\\()(\\d{1,3},\\d{1,3})(?:\\))")
        .findAll(input)
        .map {
            it.groups[1]!!.value
                .split(',')
                .map{ str -> str.toInt() }
                .reduce { a, b -> a * b }
        }.reduce{a, b -> a + b}
    println(result1)

    println("Part two:")
    val result2 = Regex("(?:don't\\(\\).*?do\\(\\).*?)*(?:mul\\()(\\d{1,3},\\d{1,3})(?:\\))", RegexOption.DOT_MATCHES_ALL)
        .findAll(input)
        .map {
            it.groups[1]!!.value
                .split(',')
                .map{ str -> str.toInt() }
                .reduce { a, b -> a * b }
        }.reduce{a, b -> a + b}
    println(result2)
}