import java.io.File
import kotlin.math.min
import kotlin.math.max
import kotlin.math.pow
import kotlin.time.measureTime

fun main() {
    fun input(): List<Triple<Int, Int, Int>> {
        return File("2025/resources/input08.txt").readLines().map { line ->
            val coords = line.split(",").map { it.toInt() }
            Triple(coords[0], coords[1], coords[2])
        }
    }

    fun part1(input: List<Triple<Int, Int, Int>>): String {
        val distances: MutableMap<Pair<Int, Int>, Double> = mutableMapOf()

        for (i in input.indices) {
            for (j in IntRange(i+1, input.size - 1)) {
                val dist = (
                    (input[j].first - input[i].first).toDouble().pow(2) +
                    (input[j].second - input[i].second).toDouble().pow(2) +
                    (input[j].third - input[i].third).toDouble().pow(2)
                )

                distances.put(Pair(min(i, j), max(i, j)), dist)
            }
        }
        val sortedDistances = distances.entries.sortedBy { it.value }

        val circuitMap = mutableMapOf<Int, Int>()
        var circuits = 0
        var connections = -1
        while (++connections < 1000) {
            val minEntry = sortedDistances.drop(connections).first()

            val minId = minEntry.key.toList().min()
            val maxId = minEntry.key.toList().max()

            val circuitId = circuitMap.getOrPut(minId, { ++circuits })
            val secCircuit = circuitMap[maxId]
            if (secCircuit == null) {
                circuitMap.put(maxId, circuitId)
            } else {
                circuitMap.filter { it.value == secCircuit }.forEach { circuitMap.set(it.key, circuitId)}
            }
        }

        input.indices.minus(circuitMap.keys).forEach {
            circuitMap.put(it, ++circuits)
        }

        val resultMap = mutableMapOf<Int, ULong>()

        circuitMap.values.toSet().forEach { id ->
            resultMap.put(id, circuitMap.filter { it.value == id }.count().toULong())
        }

        return resultMap.values.sortedDescending().take(3).reduce { acc, i -> acc * i }.toString()
    }

    fun part2(input: List<Triple<Int, Int, Int>>): String {
        val distances: MutableMap<Pair<Int, Int>, Double> = mutableMapOf()

        for (i in input.indices) {
            for (j in IntRange(i+1, input.size - 1)) {
                val dist = (
                        (input[j].first - input[i].first).toDouble().pow(2) +
                                (input[j].second - input[i].second).toDouble().pow(2) +
                                (input[j].third - input[i].third).toDouble().pow(2)
                        )

                distances.put(Pair(min(i, j), max(i, j)), dist)
            }
        }

        val sortedDistances = distances.entries.sortedBy { it.value }

        val circuitMap = mutableMapOf<Int, Int>()
        var circuits = 0
        var connections = 0
        while (!(circuitMap.values.toSet().size == 1 && circuitMap.size == input.size)) {
            val minEntry = sortedDistances.drop(connections++).first()

            val minId = minEntry.key.toList().min()
            val maxId = minEntry.key.toList().max()

            val circuitId = circuitMap.getOrPut(minId, { ++circuits })
            val secCircuit = circuitMap[maxId]
            if (secCircuit == null) {
                circuitMap.put(maxId, circuitId)
            } else {
                circuitMap.filter { it.value == secCircuit }.forEach { circuitMap.set(it.key, circuitId)}
            }
        }

        return sortedDistances.drop(connections-1).first().key.toList().fold(1UL) { acc, id -> acc * input[id].first.toULong() }.toString()
    }


    println("Day 08!")
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