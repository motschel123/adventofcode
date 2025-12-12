import java.io.File
import kotlin.time.measureTime

fun main() {
    fun input(): List<String> {
        return File("resources/input07.txt").readLines().filter { it.trim('.').isNotEmpty() }
    }

    fun part1(input: List<String>): String {
        var result = 0
        input.drop(1).fold(input.first().replace('S', '|').toCharArray() ) { prevState, currState ->
            val nextState = CharArray(prevState.size) { '.' }
            prevState.forEachIndexed { idx, c ->
                if (c == '|' && currState[idx] == '.') nextState[idx] = '|'
                else if (c == '|' && currState[idx] == '^') {
                    result++
                    nextState[idx-1] = '|'
                    nextState[idx+1] = '|'
                }
            }
            nextState
        }
        return result.toString()
    }

    fun part2(input: List<String>): String {
        val rays: Array<Array<Long>> = input.map { l -> l.map { c -> if (c == 'S') 1L else if (c == '^') -1L else 0L }.toTypedArray() }.toTypedArray()

        val finalState = rays.drop(1).runningFoldIndexed( rays.first() ) { y, prevLine, currLine ->
            val nextLine = Array(prevLine.size) { 0L }
            prevLine.forEachIndexed { x, num ->
                if (num > 0L && currLine[x] >= 0L) nextLine[x] = nextLine[x] + num
                else if (num > 0L && currLine[x] == -1L) {
                    nextLine[x-1] = nextLine[x-1] + num
                    nextLine[x+1] = nextLine[x+1] + num
                }
            }
            nextLine
        }
        finalState.map { it.map { if (it > 0L) '|' else " " } }.forEach { println(it.joinToString("")) }
        return finalState.last().sum().toString()
    }

    println("Day 07!")
    val input = input()

    val result1: String
    println("Part 1 Timing: " + measureTime {
        result1 = part1(input)
    })
    println("Part 1 Result: " + result1)

    val result2: String
    println("Part 2 Timing: " + measureTime {
        result2 = part2(input)
    })
    println("Part 2 Result: " + result2)
}