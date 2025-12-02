package org.example

import java.io.File


fun main() {
    val inputLines =
        File("src/main/resources/day06.txt").readLines()
    val data = inputLines.map { it.toCharArray().toTypedArray() }.toTypedArray()
    val moveChars: Set<Char> = setOf('<', '>', '^', 'v')

    // find start
    val startRow: Int = data.indexOfFirst {
        val idx = it.indexOfFirst { c -> moveChars.contains(c) }
        idx > -1
    }
    val startCol: Int = data[startRow].indexOfFirst { c -> moveChars.contains(c) }


    var row = startRow
    var col = startCol
    var direction = data[startRow][startCol]
    var visitedCoords: HashMap<Pair<Int, Int>, HashSet<Char>> = HashMap()
    while (true) {
        visitedCoords.getOrPut(Pair(row, col)) { HashSet() }.add(direction)

        val (nextRow, nextCol) = nextCoords(row, col, direction)
        if (nextRow < 0 ||
            nextRow >= data.size ||
            nextCol < 0 ||
            nextCol >= data[0].size)
            break

        if (data[nextRow][nextCol] == '#')
            direction = rotate90(direction)
        else {
            row = nextRow
            col = nextCol
        }
    }
    println("Part One: ${visitedCoords.keys.size}")

    var result2 = 0
    val visitedPos = visitedCoords.keys.toList()
    for ((objRow, objCol) in visitedPos) {
        if (objRow == startRow && objCol == startCol) continue

        val objData = Array(data.size) { i -> data[i].copyOf() }
        objData[objRow][objCol] = '#'

        row = startRow
        col = startCol
        direction = objData[startRow][startCol]
        visitedCoords = HashMap()
        while (true) {
            if (visitedCoords[Pair(row, col)]?.contains(direction) == true) {
                result2++
                break
            }

            visitedCoords.getOrPut(Pair(row, col)) { HashSet() }.add(direction)

            val (nextRow, nextCol) = nextCoords(row, col, direction)
            if (nextRow < 0 ||
                nextRow >= objData.size ||
                nextCol < 0 ||
                nextCol >= objData[0].size
            )
                break

            if (objData[nextRow][nextCol] == '#')
                direction = rotate90(direction)
            else {
                row = nextRow
                col = nextCol
            }
        }
    }
    println("Part Two: $result2")
}

fun nextCoords(row: Int, col: Int, dir: Char): Pair<Int, Int> {
    var nextRow = row
    var nextCol = col
    when (dir) {
        '>' -> nextCol++
        '<' -> nextCol--
        '^' -> nextRow--
        'v' -> nextRow++
    }
    return Pair(nextRow, nextCol)
}

fun rotate90(dir: Char): Char {
        return when (dir) {
            '^' -> '>'
            '>' -> 'v'
            'v' -> '<'
            '<' -> '^'
            else -> '?'
        }
    }