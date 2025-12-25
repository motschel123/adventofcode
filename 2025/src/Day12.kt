import java.io.File
import kotlin.time.measureTime

typealias INPUT12 = List<Pair<Int, Int>>

fun main() {
    fun input(): INPUT12 {
        val chunks = File("resources/input12.txt").readText().trim().split("\n\n")

        val regions_size_blocks = chunks.last().split("\n").map { line ->
            val size_map = line.split(": ")
            (
                size_map[0].split('x').map { it.toInt() }.reduce { acc, i -> acc * i }
                to
                size_map[1].split(' ').sumOf { it.toInt() }
            )
        }

        return regions_size_blocks
    }

    fun part1(input: INPUT12): String {
        return input.count { (size, numBlocks) ->
            size >= numBlocks * 9
        }.toString()
    }


    println("Day 12!")
    val input = input()

    val result1: String
    println("Part 1 Timing: " + measureTime {
        result1 = part1(input)
    })
    println("Part 1 Result: " + result1)
}