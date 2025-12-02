import java.io.File
import java.math.BigInteger

fun main() {
    val (registers, progInput) = File("./src/main/resources/day17.txt")
        .readText()
        .split("\n\n")

    val (rA, rB, rC) = Regex("\\d+").findAll(registers).toList().map { it.value.toULong() }
    val program =  Regex("\\d+").findAll(progInput).toList().map { it.value.toInt() }

    println("A: $rA, B: $rB, C: $rC")
    println(program)

    fun part1() {
        val result = mutableListOf<Int>()

        var next = executeUntilOutput(rA, rB, rC, program, 0)
        while (next != null) {
            val (nextRes, regA, regB, regC, pointer) = next
            result.add(nextRes.toInt())
            next = executeUntilOutput(regA, regB, regC, program, pointer.toInt())
        }
        println("Part One: ${result.joinToString(",")}")
    }

    fun part2() {
        var resultRegA = 0UL

        tryNextA@ while (true) {
            resultRegA++

            if (resultRegA % 100000UL == 0UL)
                printProgressBar(resultRegA, ULong.MAX_VALUE)

            var next = executeUntilOutput(resultRegA, rB, rC, program, 0)
            for (input in program) {
                if (next == null)
                    continue@tryNextA

                val (nextRes, regA, regB, regC, pointer) = next!!

                if (nextRes.toInt() != input)
                    continue@tryNextA

                next = executeUntilOutput(regA, regB, regC, program, pointer.toInt())
            }
            break
        }
        println("Part Two: $resultRegA")
    }

    part2()
}

fun executeUntilOutput(rA: ULong, rB: ULong, rC: ULong, instructions: List<Int>, p: Int): List<ULong>? {
    var regA = rA
    var regB = rB
    var regC = rC
    val program = instructions
    var pointer = p


    while (pointer >= 0 && pointer < program.size) {
        // Ensure we have at least 2 elements left to read
        if (pointer + 1 >= program.size) return null

        val opCode = instructions.getOrNull(pointer) ?: return null
        val operand = instructions.getOrNull(pointer+1) ?: return null
        val comboOperand = when (operand) {
            in 0..3 -> operand.toULong()
            4 -> regA
            5 -> regB
            6 -> regC
            else -> 0UL
        }

        when (opCode) {
            // adv: Divide A by 2^(combo operand)
            0 -> {
                regA = regA / (1UL shl comboOperand.toInt())
            }
            // bxl: Bitwise XOR B with literal operand
            1 -> {
                regB = regB xor operand.toULong()
            }
            // bst: Set B to combo operand modulo 8
            2 -> {
                regB = comboOperand % 8UL
            }
            // jnz: Jump if A is not zero
            3 -> {
                if (regA != 0UL) {
                    pointer = operand
                    continue
                }
            }
            // bxc: XOR B with C (ignore operand)
            4 -> {
                regB = regB xor regC
            }
            // out: Output combo operand modulo 8
            5 -> {
                return listOf(comboOperand % 8UL, regA, regB, regC, (pointer + 2).toULong())
            }
            // bdv: Divide A by 2^(combo operand), store in B
            6 -> {
                regB = regA / (1UL shl comboOperand.toInt())
            }
            // cdv: Divide A by 2^(combo operand), store in C
            7 -> {
                regC = regA / (1UL shl comboOperand.toInt())
            }
            else -> throw IllegalArgumentException("Unknown opcode: $opCode")
        }
        // Only increment pointer by 2 if not jumping
        pointer += 2
    }
    return null
}

fun printProgressBar(completed: ULong, total: ULong, barWidth: Int = 40) {
    val progress = (completed / total)
    val filled = (progress * barWidth.toULong()).toInt()
    val bar = "█".repeat(filled) + "-".repeat((barWidth - filled))
    val percentage = (progress * 100u).toInt()

    print("\r|$bar| $percentage%")
    if (completed == total) println() // Move to the next line when done
}