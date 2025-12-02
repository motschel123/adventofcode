import java.io.File

fun main() {
    val input = File("src/main/resources/day21.txt")
        .readLines()

    val numPad = hashMapOf(
        'A' to Pair(0, 0),
        '0' to Pair(1, 0),
        '1' to Pair(2, 1),
        '2' to Pair(1, 1),
        '3' to Pair(0, 1),
        '4' to Pair(2, 2),
        '5' to Pair(1, 2),
        '6' to Pair(0, 2),
        '7' to Pair(2, 3),
        '8' to Pair(1, 3),
        '9' to Pair(0, 3),
        ' ' to Pair(2, 0)
    )

    val keyPad = hashMapOf(
        '^' to Pair(1, 1),
        'A' to Pair(0, 1),
        '>' to Pair(0, 0),
        'v' to Pair(1, 0),
        '<' to Pair(2, 0),
        ' ' to Pair(2, 1)
    )

    fun clickPad(char: Char, pos: Pair<Int, Int>, pad: HashMap<Char, Pair<Int, Int>>): Pair<String, Pair<Int, Int>> {
        val (dx, dy) = pad[char]!!.first - pos.first to
                pad[char]!!.second - pos.second

        val result = StringBuilder()

        var curPos = pos
        while (curPos != pad[char]!!) {
            
        }

        if (dx > 0) {
            result.append("<".repeat(dx))
        }
        if (dx < 0) {
            result.append(">".repeat(-dx))
        }
        if (dy > 0) {
            result.append("^".repeat(dy))
        }
        if (dy < 0) {
            result.append("v".repeat(-dy))
        }

        result.append("A")

        return result.toString() to Pair(pos.first + dx, pos.second + dy)
    }

    fun translateLine(line: String, pad: HashMap<Char, Pair<Int, Int>>): String {
        var pos = pad['A']!!
        val result = StringBuilder()
        line.forEach { c ->
            val res = clickPad(c, pos, pad)
            result.append(res.first)
            pos = res.second
        }
        return result.toString()
    }

    val result = input.map { line ->
        println(line)
        val keypad1 = translateLine(line, numPad)
        println(keypad1)
        val keypad2 = translateLine(keypad1, keyPad)
        println(keypad2)
        val keypad3 = translateLine(keypad2, keyPad)
        println(keypad3)
        line to keypad3
    }

    println(checksum(result))
}

fun checksum(result: List<Pair<String, String>>): Int {
    return result.sumOf{
        it.first.filter { c -> c.isDigit() }.toInt() * it.second.length
    }
}