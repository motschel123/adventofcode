import java.io.File
import java.math.BigInteger

fun main() {
    val input = File("src/main/resources/day24.txt").readText()

    val (initsStr, compsStr) = input.split("\n\n")
    val inits = initsStr.split("\n").filter { it.isNotEmpty() }
    val comps = compsStr.split("\n").filter { it.isNotEmpty() }

    val values = mutableMapOf<String, Int>()

    inits.forEach {
        val (k, v) = it.split(": ")
        values[k] = v.toInt()
    }

    val gates = comps.toMutableList()

    while (gates.isNotEmpty()) {
        if (gates.none { it.split("-> ")[1].startsWith("z") }) break

        val gate = gates.removeFirst()
        val (l, op, r, _, k) = gate.split(" ")

        if (values.containsKey(l) && values.containsKey(r)) {
            when(op) {
                "XOR" -> values.putIfAbsent(k, values[l]!!.xor(values[r]!!))
                "AND" -> values.putIfAbsent(k, values[l]!!.and(values[r]!!))
                "OR" -> values.putIfAbsent(k, values[l]!!.or(values[r]!!))
                else -> println("Unknown operation: $op")
            }
        } else
            gates.addLast(gate)
    }

    val result = StringBuilder()
    values.keys
        .filter {
            it.startsWith("z")
        }
        .sortedDescending().forEach { k ->
            result.append(values.getOrDefault(k, 0))
        }

    println("Part One!")
    println(BigInteger(result.toString(), 2))

    val outputWires = comps
        .filter { it.contains("AND") }
        .filter {
            val (l, op, r, _, k) = it.split(" ")
            l.startsWith("x") ||
                    l.startsWith("y") ||
                    r.startsWith("x") ||
                    r.startsWith("y")
        }
        .sortedBy {
            it.split(" ")[0].substring(1).toInt()
        }
        .map { it.split(" -> ")[1] }

    outputWires.zip(outputWires.sorted()).forEach(::println)

    fun <T> List<T>.combinationsOfSize(elements: List<T>, size: Int): Sequence<List<T>> = sequence {
        if (size == 0) {
            yield (elements)
        } else
            for (i in this@combinationsOfSize) {
                this@combinationsOfSize.minus(i).combinationsOfSize(elements.plus(i), size - 1).forEach { yield(it) }
            }
    }
}