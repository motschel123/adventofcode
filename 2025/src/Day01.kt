import java.io.File
import kotlin.collections.fold
import kotlin.collections.map
import kotlin.io.readLines
import kotlin.math.abs
import kotlin.text.first
import kotlin.text.substring
import kotlin.text.toInt

fun main() {
    println("Day 1!")

    val lines = File("resources/input01.txt").readLines()

    val rotations = lines.map {
        val value = it.substring(1).toInt()
        if (it.first() == 'R') value else -value
    }

    var currPos = 50

    val numZero = rotations.fold(0) { acc, i ->
        currPos = (currPos + i) % 100
        if (currPos == 0) acc + 1 else acc
    }

    println("Part 1: $numZero")

    currPos = 50

    val numZero2 = rotations.fold(0) { acc, i ->
        val prevPos = currPos
        var incr = abs(i / 100)
        val rot = i % 100

        if (rot != 0) {
            currPos += rot
            if (currPos == 0) incr++
            if (currPos >= 100) {
                currPos %= 100
                incr++
            }
            if (currPos < 0) {
                currPos = 100 - abs(currPos)
                if (prevPos > 0) incr++
            }
        }
        acc + incr
    }

    println("Part 2: $numZero2")

}