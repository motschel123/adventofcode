package org.example

import java.io.File

fun main() {
    val inputLines =
        File("src/main/resources/day08.txt").readLines()
    val data = inputLines.map { it.toCharArray().toTypedArray() }.toTypedArray()


    val antennaCollection: HashMap<Char, MutableList<Pair<Int, Int>>> = HashMap()
    data.forEachIndexed{ rowIdx, row ->
        row.forEachIndexed{ colIdx, char ->
            if (char != '.') antennaCollection.getOrPut(char) { mutableListOf() }.add(Pair(rowIdx, colIdx))
        }
    }

    val antiNodePositions1 = mutableSetOf<Pair<Int, Int>>()
    antennaCollection.forEach{ (_, antennas) ->
        antennas.forEachIndexed {idx, pos ->
            val otherAntennas = antennas.subList(idx+1,  antennas.size)

            otherAntennas.forEach { otherPos ->
                val distance = Pair(
                    otherPos.first - pos.first,
                    otherPos.second - pos.second
                )
                val antiNode1 = Pair(
                    pos.first - distance.first,
                    pos.second - distance.second
                )
                if (
                    antiNode1.first in data.indices &&
                    antiNode1.second in data[0].indices)
                    antiNodePositions1.add(antiNode1)
                val antiNode2 = Pair(
                    otherPos.first + distance.first,
                    otherPos.second + distance.second
                )
                if (
                    antiNode2.first in data.indices &&
                    antiNode2.second in data[0].indices)
                    antiNodePositions1.add(antiNode2)
            }
        }
    }
    println("Part One: ${antiNodePositions1.size}")

    val antiNodePositions2 = mutableSetOf<Pair<Int, Int>>()
    antennaCollection.forEach { (_, antennas) ->
        antennas.forEachIndexed { idx, pos ->
            val otherAntennas = antennas.subList(idx + 1, antennas.size)

            otherAntennas.forEach { otherPos ->
                val distance = Pair(
                    otherPos.first - pos.first,
                    otherPos.second - pos.second
                )

                var i = 0
                var antiNodePos: Pair<Int, Int>
                while (true) {
                    antiNodePos = Pair(
                        pos.first - i * distance.first,
                        pos.second - i * distance.second
                    )

                    if(!(antiNodePos.first in data.indices &&
                        antiNodePos.second in data[0].indices))
                        break

                    antiNodePositions2.add(antiNodePos)
                    i++
                }

                i = 0
                while (true) {
                    antiNodePos = Pair(
                        otherPos.first + i * distance.first,
                        otherPos.second + i * distance.second
                    )
                    if (!(antiNodePos.first in data.indices &&
                        antiNodePos.second in data[0].indices))
                        break

                    antiNodePositions2.add(antiNodePos)
                    i++
                }
            }
        }
    }

    println("Part Two: ${antiNodePositions2.size}")
}