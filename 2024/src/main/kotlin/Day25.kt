import java.io.File
import java.util.*

fun main() {
    val input = File("src/main/resources/day25.txt")
        .readText()
        .split("\n\n")
        .map { it.split("\n") }
        .groupBy { if (it.first().contains('#')) "locks" else "keys" }
        .mapValues { (_, locks_keys) ->
            locks_keys.map {
                val heights = Array(it[0].length) { -1 }
                it.forEach{ line ->
                    line.forEachIndexed{ idx: Int, c: Char ->
                        if (c == '#') {
                            heights[idx]++
                        }
                    }
                }
                heights
            }
        }

    var result1 = 0
    input["locks"]!!.forEach { lock ->
        input["keys"]!!.forEach { key ->
            var valid = true
            for (i in lock.indices) {
                if (lock[i] + key[i] > 5) {
                    valid = false
                    break
                }
            }
            if (valid) result1 += 1
        }
    }
    println("Part One: $result1")
}