package org.example

import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.PrintStream

val width = 101
val height = 103

fun main() {
    val robots = File("src/main/resources/Day14.txt")
        .readLines()
        .map { line ->
            Regex("-?\\d+").findAll(line)
            .map { it.value.toInt() }
        }
        .map { it.toList().toTypedArray() }

    val neighbourScores = mutableListOf<Int>()
    var nextRobots = robots
    for (i in 1..10404) {
        nextRobots = nextRobots.map(::takeStep)

        if (i == 100) {
            val result1 = nextRobots
                .asSequence()
                .filter {
                    it[0] != width / 2
                            && it[1] != height / 2
                }
                .groupBy {
                    if (it[0] < width / 2)
                        if (it[1] < height / 2)
                            1
                        else
                            3
                    else
                        if (it[1] > height / 2)
                            2
                        else
                            4
                }
                .map { it.value.size }
                .reduce { acc, e -> acc * e }
            println("Part One: $result1")
        }

        val arr = Array(height) { Array(width) {'.'} }
        var tree = true
        for (robot in nextRobots) {
            if (arr[robot[1]][robot[0]] == '#') {
                tree = false
                break
            }
            arr[robot[1]][robot[0]] = '#'
        }
        if (tree) {
            println("Tree: $i")
            arr.forEach { println(it.joinToString("")) }
        }
        neighbourScores.add(arr.flatten().mapIndexed { j, c ->
            if (c != '#')
                0
            else {
                val y = j / width
                val x = j % width

                var score = 0
                if (arr.getOrNull(y+1)?.get(x) == '#')
                    score++
                if (arr.getOrNull(y-1)?.get(x) == '#')
                    score++
                if (arr.get(y).getOrNull(x+1) == '#')
                    score++
                if (arr.get(y).getOrNull(x-1) == '#')
                    score++
                score
            }
        }.sum())
    }
    println("Part Two: " + neighbourScores.indexOf(neighbourScores.max()) + 1)
}

fun takeStep(robot: Array<Int>): Array<Int> {
    return arrayOf(
        ((robot[0] + robot[2]) % width + width) % width,
        ((robot[1] + robot[3]) % height + height) % height,
        robot[2],
        robot[3]
    )
}