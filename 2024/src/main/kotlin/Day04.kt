package org.example

import java.io.File

fun main() {
    val input = File("src/main/resources/day04.txt").readLines().map { it.toCharArray().toTypedArray() }.toTypedArray()


    println("Part One:")
    var result1 = 0
    for (row in input.indices) {
        for (col in input.indices) {
            if (input[row][col] == 'X') {
                // Horizontal
                if (input[row].getOrNull(col+1) == 'M' &&
                    input[row].getOrNull(col+2) == 'A' &&
                    input[row].getOrNull(col+3) == 'S')
                    result1++
                if (input[row].getOrNull(col-1) == 'M' &&
                    input[row].getOrNull(col-2) == 'A' &&
                    input[row].getOrNull(col-3) == 'S')
                    result1++

                // Vertical
                if (input.getOrNull(row+1)?.get(col) == 'M' &&
                    input.getOrNull(row+2)?.get(col) == 'A' &&
                    input.getOrNull(row+3)?.get(col) == 'S')
                    result1++
                if (input.getOrNull(row-1)?.get(col) == 'M' &&
                    input.getOrNull(row-2)?.get(col) == 'A' &&
                    input.getOrNull(row-3)?.get(col) == 'S')
                    result1++

                // Diagonal
                if (input.getOrNull(row+1)?.getOrNull(col+1) == 'M' &&
                    input.getOrNull(row+2)?.getOrNull(col+2) == 'A' &&
                    input.getOrNull(row+3)?.getOrNull(col+3) == 'S')
                    result1++
                if (input.getOrNull(row+1)?.getOrNull(col-1) == 'M' &&
                    input.getOrNull(row+2)?.getOrNull(col-2) == 'A' &&
                    input.getOrNull(row+3)?.getOrNull(col-3) == 'S')
                    result1++
                if (input.getOrNull(row-1)?.getOrNull(col+1) == 'M' &&
                    input.getOrNull(row-2)?.getOrNull(col+2) == 'A' &&
                    input.getOrNull(row-3)?.getOrNull(col+3) == 'S')
                    result1++
                if (input.getOrNull(row-1)?.getOrNull(col-1) == 'M' &&
                    input.getOrNull(row-2)?.getOrNull(col-2) == 'A' &&
                    input.getOrNull(row-3)?.getOrNull(col-3) == 'S')
                    result1++
            }
        }
    }
    println(result1)

    println("Part Two:")
    var result2 = 0
    for (row in input.indices) {
        for (col in input.indices) {
            if (input[row][col] == 'A') {
                if ((
                        (input.getOrNull(row-1)?.getOrNull(col-1) == 'M' && input.getOrNull(row+1)?.getOrNull(col+1) == 'S') ||
                        (input.getOrNull(row-1)?.getOrNull(col-1) == 'S' && input.getOrNull(row+1)?.getOrNull(col+1) == 'M')
                    ) && (
                        (input.getOrNull(row+1)?.getOrNull(col-1) == 'M' && input.getOrNull(row-1)?.getOrNull(col+1) == 'S') ||
                        (input.getOrNull(row+1)?.getOrNull(col-1) == 'S' && input.getOrNull(row-1)?.getOrNull(col+1) == 'M')
                    )
                )
                    result2++
            }
        }
    }
    println(result2)
}