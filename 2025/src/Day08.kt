import java.io.File
import kotlin.math.min
import kotlin.math.max
import kotlin.math.pow
import kotlin.time.measureTime

fun main() {
    fun input(): List<Triple<Int, Int, Int>> {
        return File("resources/input08.txt").readLines().map { line ->
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

        val circuits = mutableMapOf<Int, MutableSet<Int>>()
        val idMappings = mutableMapOf<Int, Int>()
        var connections = 0
        var nextCircuitId = 1

        while (connections < 1000) {
            val minEntry = sortedDistances.get(connections++)

            val firstId = idMappings[minEntry.key.first]
            val secondId = idMappings[minEntry.key.second]

            if (firstId != null && secondId != null) {
                if (firstId == secondId) continue

                val combineId = min(firstId, secondId)
                val removeId = max(firstId, secondId)

                circuits[combineId]!!.addAll(circuits[removeId].orEmpty())
                idMappings.putAll(circuits[removeId]!!.map { id -> Pair(id, combineId) })
                circuits.remove(removeId)
            } else if (firstId != null) {
                circuits[firstId]!!.add(minEntry.key.second)
                idMappings.put(minEntry.key.second, firstId)
            } else if (secondId != null) {
                circuits[secondId]!!.add(minEntry.key.first)
                idMappings.put(minEntry.key.first, secondId)
            } else {
                circuits.put(nextCircuitId, mutableSetOf(minEntry.key.first, minEntry.key.second))
                idMappings.put(minEntry.key.first, nextCircuitId)
                idMappings.put(minEntry.key.second, nextCircuitId)
                nextCircuitId++
            }
        }

        return circuits.values.map{ it.size }.sortedDescending().take(3).reduce { acc, i -> acc * i }.toString()
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

        val circuits = mutableMapOf<Int, MutableSet<Int>>()
        val idMappings = mutableMapOf<Int, Int>()
        var connections = 0
        var nextCircuitId = 1
        while (!(circuits.size == 1 && circuits.any { it.value.size == input.size })) {
            val minEntry = sortedDistances.get(connections++)

            val firstId = idMappings[minEntry.key.first]
            val secondId = idMappings[minEntry.key.second]

            if (firstId != null && secondId != null) {
                if (firstId == secondId) continue

                val combineId = min(firstId, secondId)
                val removeId = max(firstId, secondId)

                circuits[combineId]!!.addAll(circuits[removeId].orEmpty())
                idMappings.putAll(circuits[removeId]!!.map { id -> Pair(id, combineId) })
                circuits.remove(removeId)
            } else if (firstId != null) {
                circuits[firstId]!!.add(minEntry.key.second)
                idMappings.put(minEntry.key.second, firstId)
            } else if (secondId != null) {
                circuits[secondId]!!.add(minEntry.key.first)
                idMappings.put(minEntry.key.first, secondId)
            } else {
                circuits.put(nextCircuitId, mutableSetOf(minEntry.key.first, minEntry.key.second))
                idMappings.put(minEntry.key.first, nextCircuitId)
                idMappings.put(minEntry.key.second, nextCircuitId)
                nextCircuitId++
            }
        }

        return sortedDistances.get(connections-1).key.toList().fold(1UL) { acc, id -> acc * input[id].first.toULong() }.toString()
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