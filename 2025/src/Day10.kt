import java.io.File
import java.util.BitSet

import kotlin.time.measureTime


typealias INPUT10 = List<Triple<BitSet, List<BitSet>, Array<Int>>>
fun main() {

    fun input(): INPUT10 {
        val regex = Regex("^\\[(?<targetLEDs>[.#]+)\\] (?<buttons>\\(.*\\)) (?<joltages>\\{.*\\})$")
        return File("resources/input10.txt").readLines().map { line ->
            val find = regex.find(line)
            if (find == null) throw Exception("Invalid input line: $line")
            val targetLedStr = find.groups["targetLEDs"]!!.value
            val buttonsStr = find.groups["buttons"]!!.value.replace(Regex("[\\(\\)]"),"")
            val joltagesStr = find.groups["joltages"]!!.value.replace(Regex("[\\{\\}]"), "")


            Triple(
                BitSet(targetLedStr.length).also { bs -> targetLedStr.forEachIndexed { i, c -> bs.set(i, c == '#') } },
                buttonsStr
                    .split(' ')
                    .map { btnStr ->
                        val ints = btnStr.split(',').map { it.toInt() }
                        BitSet(targetLedStr.length).also { bs -> ints.forEach { bs.set(it) } }
                    },
                joltagesStr.split(',').map { it.toInt() }.toTypedArray()
            )
        }
    }

    fun part1(input: INPUT10): String {
        fun step(state: Triple<BitSet, List<BitSet>, Int>): List<Triple<BitSet, List<BitSet>, Int>> {
            // Buttons that flip at least one relevant bit
            return state.second
                .filter { it.intersects(state.first) }
                .sortedByDescending { btn -> (btn.clone() as BitSet).also { it.and(state.first) }.cardinality() }
                .map { button ->
                    Triple(
                        (state.first.clone() as BitSet).also{ it.xor(button) }, // flip all leds specified in button
                        state.second.filter { it != button }.map { it.clone() as BitSet },
                        state.third + 1
                    ) }
        }

        val fewestButtonPresses = input.mapIndexed { idx, (startLeds, buttons, _) ->
            val stateQueue = ArrayDeque(listOf(Triple(startLeds, buttons,  0)))

            var bestSolution: Triple<BitSet, List<BitSet>, Int>? = null
            while (stateQueue.isNotEmpty()) {
                stateQueue.first().let {
                    if (it.first.cardinality() == 0 && (bestSolution == null || it.third < bestSolution.third)) bestSolution = it
                }
                stateQueue.addAll(0, step(stateQueue.removeFirst()))
                if (bestSolution != null) {
                    stateQueue.removeAll { it.third >= bestSolution.third }
                }
            }

            bestSolution!!.third
        }

        return fewestButtonPresses.sum().toString()
    }

    fun part2(input: INPUT10): String {
        return ""
    }

    println("Day 10!")
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