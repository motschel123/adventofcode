package org.example

import java.io.File

fun main() {
    val input =
        File("src/main/resources/day07.txt").readLines().map {
            val (total, numbers) = it.split(": ")
            Pair(total.toLong(), numbers.split(" ").map(String::toLong))
        }

    val validOperators1 = listOf<(Long, Long) -> Long>(
        { a, b -> a + b },
        { a, b -> a * b }
    )

    val validOperators2 = listOf<(Long, Long) -> Long>(
        { a, b -> a + b },
        { a, b -> a * b },
        { a, b -> (a.toString() + b.toString()).toLong()}
    )


    val result1 = input
        .zip(
            input.map { (total, numbers) ->
                checkValid(total, numbers, validOperators1)
            }
        )
        .filter { it.second }
        .map { it.first.first }
        .reduce{ acc, it -> acc + it}

    println("Part One: $result1")

    val result2 = input
        .zip(
            input.map { (total, numbers) ->
                checkValid(total, numbers, validOperators2)
            }
        )
        .filter { it.second }
        .map { it.first.first }
        .reduce{ acc, it -> acc + it}

    println("Part One: $result2")
}

fun checkValid(total: Long, numbers: List<Long>, operators: List<(Long, Long) -> Long>): Boolean {
    if (numbers.size == 1) return numbers[0] == total
    for (operator in operators) {
        if (checkValid(
                total,
                listOf(operator(numbers[0], numbers[1])) + numbers.subList(2, numbers.size),
                operators
        ))
            return true
    }
    return false
}
