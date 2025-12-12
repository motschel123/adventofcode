import java.io.File
import kotlin.time.measureTime

typealias MAP11 = Map<String, List<String>>
typealias INPUT11 = Pair<MAP11, MAP11>

fun main() {
    fun input(): INPUT11 {
        val lines = File("resources/input11.txt").readLines().map{ it.replace(":", "").split(' ') }

        val forwardsMapping = lines.associate { line ->
                Pair(line.first(), line.drop(1))
            }
        val backwardsMapping = mutableMapOf<String, MutableList<String>>()
        lines.forEach { line ->
            line.drop(1).forEach {
                backwardsMapping.getOrPut(it) { mutableListOf() }.add(line.first())
            }
        }
        return forwardsMapping to backwardsMapping
    }

    fun visitedConnections(from: String, to: String, visited: MutableMap<String, Long>, mapping: MAP11): Long {
        if (!visited.containsKey(to))
            visited.put(to, 1L)
        val stack = ArrayDeque(listOf(from))
        while (stack.isNotEmpty()) {
            val currNode = stack.first()

            val nextNodes = mapping.getOrDefault(currNode, emptyList())
            val unvisitedNextNodes = nextNodes.filter { !visited.containsKey(it) }
            if (unvisitedNextNodes.isEmpty()) {
                visited.putIfAbsent(currNode, nextNodes.sumOf{ visited.getOrPut(it) { 0L } })
                stack.removeFirst()
                continue
            }

            stack.addAll(0, nextNodes)
        }

        return visited[from]!!
    }

    fun part1(input: INPUT11): String {
        return visitedConnections("you", "out", mutableMapOf(), input.first).toString()
    }

    fun part2(input: INPUT11): String {
        val svrToFtt = visitedConnections("fft", "svr", mutableMapOf(), input.second)

        val dacToOutVisited = mutableMapOf<String, Long>()
        val dacToOut = visitedConnections("dac", "out", dacToOutVisited, input.first)

        val mapping = input.first.filter { !dacToOutVisited.containsKey(it.key) }

        val fttToDacVisited = dacToOutVisited.mapValues { 0L }.toMutableMap().also { it.put("dac", 1L) }
        val fttToDac = visitedConnections("fft", "dac", fttToDacVisited, mapping)

        return (svrToFtt * fttToDac * dacToOut).toString()
    }

    println("Day 11!")
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

