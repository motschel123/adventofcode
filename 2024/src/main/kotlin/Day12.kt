package org.example

import java.io.File

fun handlePlant1(row: Int, col: Int, field: Array<Array<Char>>): Pair<Int, Int> {
    val plant = field[row][col]
    if (plant.isLowerCase())
        return Pair(0,0)
    field[row][col] = field[row][col].lowercaseChar()

    val surroundingPlants = surroundingPlants(plant, row, col, field)
    return (surroundingPlants
        .map {
            handlePlant1(it.first, it.second, field)
        }
        .reduceOrNull{ acc, pair -> Pair(acc.first + pair.first, pair.second + acc.second) }
        ?:Pair(0,0)
            )
        .let { Pair(
            it.first + 1,
            it.second + 4 - surroundingPlants.size
        )}
}


fun main() {
    val input = File("src/main/resources/day12.txt")
        .readLines()
        .filter { it.isNotEmpty() }
        .map { it.toCharArray().toTypedArray() }.toTypedArray()

    println("Part One: ")
    val input1 = Array(input.size) { input[it].copyOf() }
    var row = input1.indexOfFirst{
        it.indexOfFirst{c -> c.isUpperCase()} != -1
    }
    var col = input1[row].indexOfFirst{c -> c.isUpperCase()}

    var result1 = 0
    while (row != -1 && col != -1) {
        val (area, perimeter) = handlePlant1(row, col, input1)
        result1 += area * perimeter

        row = input1.indexOfFirst{
            it.indexOfFirst{c -> c.isUpperCase()} != -1
        }
        col = if(row != -1) input1[row].indexOfFirst{c -> c.isUpperCase()} else -1
    }
    input1.forEach{ println(it.joinToString(""))}

    println(result1)


    println("Part Two:")
    val input2 = Array(input.size) { input[it].copyOf() }

    var region = findNextRegion(input2)
    var totalPrice = 0
    while (region != null) {
        region.forEach { println(it.joinToString("")) }

        val vertLines = calculateUniqueVerticalLines(region)
        println("Vertical Lines: $vertLines")

        val horiLines = calculateUniqueVerticalLines(transposeMatrix(region))
        println("Horizontal Lines: $horiLines")

        totalPrice += (vertLines + horiLines) * region.sumOf { it.count(Char::isLetter) }

        region = findNextRegion(input2)
    }
    println(totalPrice)
}

fun transposeMatrix(matrix: Array<Array<Char>>): Array<Array<Char>> {
    val rows = matrix.size
    val cols = matrix[0].size
    val transposed = Array(cols) { Array(rows) { ' ' } } // Create a new array with flipped dimensions

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            transposed[j][i] = matrix[i][j] // Swap rows and columns
        }
    }
    return transposed
}

fun calculateUniqueVerticalLines(field: Array<Array<Char>>): Int {
    var uniqueVertLines = 0

    val vertLines = field.flatMapIndexed{ i, row ->
        listOf(
            row.mapIndexed{ j, char ->
                if (char.isLetter() && (field.getOrNull(i-1)?.getOrNull(j)?.isLetter() != true)) '-'
                else ' '
            },
            row.mapIndexed{ j, char ->
                if (char.isLetter() && (field.getOrNull(i+1)?.getOrNull(j)?.isLetter() != true)) '-'
                else ' '
            }
        )
    }

    vertLines.forEach { row ->
        var uniqueLineStart = row.indexOfFirst {c -> c == '-'}
        var lineEnd = -1
        while (uniqueLineStart != -1 && uniqueLineStart < row.size) {
            uniqueVertLines++
            lineEnd = row.slice(uniqueLineStart..<row.size).indexOfFirst { c -> c != '-' }
            if (lineEnd != -1) lineEnd += uniqueLineStart
            if (lineEnd == -1 || lineEnd >= row.size) break
            uniqueLineStart = row.slice(lineEnd+1..<row.size).indexOfFirst { c -> c == '-'}
            if (uniqueLineStart != -1) uniqueLineStart += lineEnd+1
        }
    }

    return uniqueVertLines
}

fun plantsInRegion(plant: Char, row: Int, col: Int, field: Array<Array<Char>>): List<Pair<Int, Int>> {
    if (field[row][col].isLowerCase())
        return emptyList()

    field[row][col] = field[row][col].lowercaseChar()

    return surroundingPlants(plant, row, col, field)
        .flatMap {
            plantsInRegion(field[row][col], it.first, it.second, field)
        } + Pair(row, col)
}

fun findNextRegion(field: Array<Array<Char>>): Array<Array<Char>>? {
    val row = field.indexOfFirst{
        it.indexOfFirst{c -> c.isUpperCase()} != -1
    }
    if (row == -1) return null
    val col = field[row].indexOfFirst{c -> c.isUpperCase()}
    if (col == -1) return null

    val regionChar = field[row][col]
    val plants = plantsInRegion(regionChar, row, col, field)

    var minRow = Int.MAX_VALUE
    var minCol = Int.MAX_VALUE
    var maxRow = -1
    var maxCol = -1
    plants.forEach{ (row, col) ->
        minRow = minOf(minRow, row)
        minCol = minOf(minCol, col)
        maxRow = maxOf(maxRow, row)
        maxCol = maxOf(maxCol, col)
    }

    val sizeRow = maxRow - minRow + 1
    val sizeCol = maxCol - minCol + 1


    val res = Array(sizeRow) { Array(sizeCol) { ' ' } }
    plants.forEach{ (r, c) ->
        res[r-minRow][c-minCol] = regionChar
    }

    return res
}

fun surroundingPlants(plant: Char, row:Int, col: Int, field: Array<Array<Char>>): List<Pair<Int, Int>> {
    fun helper(r: Int, c: Int): Pair<Int, Int>? {
        return if (field.getOrNull(r)?.getOrNull(c)?.lowercaseChar() == plant.lowercaseChar())
            Pair(r, c)
        else
            null
    }

    return listOfNotNull(
        helper(row-1, col),
        helper(row+1, col),
        helper(row, col-1),
        helper(row, col+1),
    )
}