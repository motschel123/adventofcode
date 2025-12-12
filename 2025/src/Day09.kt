import java.io.File
import kotlin.math.abs
import kotlin.time.measureTime


fun main() {
    fun input(): List<Pair<Int, Int>> {
        return File("resources/input09.txt").readLines()
            .map { it.split(',').map { it.toInt() } }.map { Pair(it[0], it[1]) }
    }

    fun part1(input: List<Pair<Int, Int>>): String {
        var maxArea = Long.MIN_VALUE
        input.forEachIndexed { i, pair ->
            input.forEachIndexed { j, pair2 ->
                val area = (abs(pair.first - pair2.first) + 1).toLong() * (abs(pair.second - pair2.second) + 1).toLong()
                if (area > maxArea) {
                    maxArea = area
                }
            }
        }

        return maxArea.toString()
    }

//    fun part2(input: List<Pair<Int, Int>>): String {
//        // UDLR
//        val dirs = input.mapIndexed { i, it ->
//            val dirs = BitSet(4)
//            val neighbour1 = input[(i + 1) % input.size]
//            val neighbour2 = input[if (i == 0) input.size-1 else i - 1]
//
//            if (neighbour1.second < it.second || neighbour2.second < it.second)
//                dirs.set(0) // UP
//            if (neighbour1.second > it.second || neighbour2.second > it.second)
//                dirs.set(1) // DOWN
//            if (neighbour1.first < it.first || neighbour2.first < it.first)
//                dirs.set(2) // LEFT
//            if (neighbour1.first > it.first || neighbour2.first > it.first)
//                dirs.set(3) // RIGHT
//            dirs
//        }
//
//        val width = input.maxOf { it.first } + 1
//        val edgeMap = BitSet(Long)
//
//        input.forEachIndexed { i, it ->
//            edgeMap.set(it.first + it.second * width, true)
//            val neighbour1 = input[(i + 1) % input.size]
//            val neighbour2 = input[if (i == 0) input.size-1 else i - 1]
//
//
//            // Set Edges
//            for (neighbour in listOf(neighbour1, neighbour2)) {
//                val minX = min(it.first, neighbour.first)
//                val maxX = max(it.first, neighbour.first)
//                val minY = min(it.second, neighbour.second)
//                val maxY = max(it.second, neighbour.second)
//
//                if (neighbour.second == it.second) {
//                    for (x in minX..maxX)
//                        edgeMap.set(x + it.second * width, true)
//                }
//                if (neighbour.first == it.first)
//                    for (y in minY..maxY) edgeMap.set(it.first + y * width, true)
//            }
//        }
//
//        var largestArea = Long.MIN_VALUE
//        val positions = Array<Pair<Int, Int>>(2) { Pair(0,0) }
//        for (pos in input) {
//            for (pos2 in input.map { Pair(it,(1L + abs(it.first - pos.first)) * (1L + abs(it.second - pos.second))) }.sortedByDescending { it.second}) {
//                if (pos2.second <= largestArea) break
//
//                val minX = min(pos.first, pos2.first.first)
//                val maxX = max(pos.first, pos2.first.first)
//                val minY = min(pos.second, pos2.first.second)
//                val maxY = max(pos.second, pos2.first.second)
//
//                if (input.withIndex().any {
//                    val value = it.value
//                    val dir = dirs[it.index]
//                    val check = ((value.first in minX+1..maxX-1 && value.second in minY+1..maxY-1) ||
//                        ((value.second in minY+1..maxY-1 && (
//                            // left & right
//                            (value.first == minX && dir.get(3)) ||
//                            // right & left
//                            (value.first == maxX && dir.get(2))
//                        )) ||
//                        ((value.first in minX + 1..maxX - 1) && (
//                                // upper & down
//                                (value.second == minY && dir.get(1)) ||
//                                // lower up
//                                (value.second == maxY && dir.get(0))
//                        ))))
//                    check
//                }) {
//                    continue
//                }
//
//                if (edgeMap.withIndex().filter{ it.value }.any { it ->
//                    it.index % width in minX+1..maxX-1 && it.index / width in minY+1..maxY-1 })
//                    continue
//
//                largestArea = pos2.second
//                positions[0] = pos
//                positions[1] = pos2.first
//            }
//        }
//        println("${positions[0]}, ${positions[1]}")
//
//        return largestArea.toString()
//    }

    println("Day 09!")
    val input = input()

    val result1: String
    println("Part 1 Timing: " + measureTime {
        result1 = part1(input)
    })
    println("Part 1 Result: " + result1)

//    val result2: String
//    println("Part 2 Timing: " + measureTime {
//        result2 = part2(input)
//    })
//    println("Part 2 Result: " + result2)
}