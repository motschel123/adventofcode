package org.example

import java.io.File


fun main() {
    println("Day 1!")

    val lines = File("src/main/resources/day01.txt").readLines()

    val leftIds = lines.map { it.split("\\s+".toRegex())[0].toInt() }
    val rightIds = lines.map { it.split("\\s+".toRegex())[1].toInt() }

    val totalDistance =
        leftIds.sorted()
            .zip(
                rightIds.sorted()).stream()
            .map { (first, second) ->
                Math.abs(first - second)
            }.reduce(0) { acc, value -> acc + value }

    println("Part 1, Total distance: $totalDistance")

    val similarityScore = leftIds.stream().map { id ->
        id * rightIds.count { it == id }
    }.reduce(0) { acc, value -> acc + value }

    println("Part 2: $similarityScore")
}