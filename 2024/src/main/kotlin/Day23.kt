import java.io.File

fun main() {
    val input = File("src/main/resources/day23.txt")
        .readLines()
        .map { it.split("-") }

    val connections = mutableMapOf<String, MutableSet<String>>()

    input.forEach {
        connections.getOrPut(it[0]) { mutableSetOf() }.add(it[1])
        connections.getOrPut(it[1]) { mutableSetOf() }.add(it[0])
    }

    val triplets = mutableSetOf<Set<String>>()

    var unvisited = connections.keys.toMutableSet()

    while (unvisited.isNotEmpty()) {
        val current = unvisited.first()
        unvisited.remove(current)

        if (current == "de")
            println("Found de")

        val neighbors = connections[current] ?: emptySet()
        for (neighbor1 in neighbors) {
            for (neighbor2 in connections[neighbor1] ?: emptySet()) {
                if (neighbors.contains(neighbor2)) {
                    triplets.add(setOf(current, neighbor1, neighbor2))
                }
            }
        }
    }

    val result = triplets
        .filter { triple ->
            triple.any { it.startsWith("t") }
        }
        .count()

    println("Part One: $result")


    var biggestParty = setOf<String>()
    unvisited = connections.keys.toMutableSet()
    loop@while (unvisited.isNotEmpty()) {
        val current = unvisited.first()
        unvisited.remove(current)

        val currentSet = connections[current]?.plus(current) ?: emptySet()
        val currentConnections = connections
            .filter {
                it.key == current || it.value.contains(current)
            }.map {
                it.key to it.value.filter { v -> currentSet.contains(v) }
            }.toMap()

        val connectedPC = currentSet
            .map { Pair(it, currentConnections[it]?.size ?: 0) }
            .sortedByDescending { it.second }

        val numCon = connectedPC[1].second
        val possibleParty = connectedPC.takeWhile { it.second >= numCon }

        if (possibleParty.size > biggestParty.size) {
            // Sanity check
            for ((c, _) in possibleParty) {
                if (connections[c]?.containsAll(possibleParty.filter { it.first != c }.map { it.first }) != true)
                    continue@loop
            }
            biggestParty = possibleParty.map { it.first }.toSet()
        }
    }

    println("Part Two: ${biggestParty.sorted().joinToString(",")}")
}