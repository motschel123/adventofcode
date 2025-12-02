package org.example

import java.io.File

fun main() {
    val input = File("src/main/resources/Day15.txt").readText()
    var (initalMap, moves) = input.split("\n\n")
    moves = moves.filter { charArrayOf('<', '>', '^', 'v').contains(it) }

    fun part1() {
        println("Part One: ")
        val height = initalMap.split("\n").size
        val width = initalMap.split("\n").first().length

        val initialState = mutableMapOf<Pair<Int,Int>, Char>()
        var pos: Pair<Int,Int> = Pair(0,0)

        initalMap.split("\n").forEachIndexed{ y, line ->
            line.toCharArray().forEachIndexed { x, c ->
                if (c != '.')
                    initialState[Pair(y, x)] = c
                if (c == '@')
                    pos = Pair(y, x)
            }
        }

        var nextState = initialState.toMap()
        var nextPos: Pair<Int,Int> = pos
        for (move in moves) {
            val next = push(nextPos.first, nextPos.second, move, nextState) ?: continue
            nextPos = next.first
            nextState = next.second
        }
        printMap(nextState, width, height)
        println(checksum2(nextState))
    }

    fun part2() {
        println("Part Two: ")
        val height = initalMap.split("\n").size
        val width = initalMap.split("\n").first().length * 2
        moves = moves.filter { charArrayOf('<', '>', '^', 'v').contains(it) }

        val initialState = mutableMapOf<Pair<Int, Int>, Char>()
        var pos: Pair<Int, Int> = Pair(0, 0)
        initalMap.split("\n").forEachIndexed { y, line ->
            line.toCharArray().forEachIndexed { x, c ->
                when (c) {
                    '#' -> {
                        initialState[y to x*2] = c
                        initialState[y to x*2+1] = c
                    }
                    'O' -> {
                        initialState[y to x*2] = '['
                        initialState[y to x*2+1] = ']'
                    }
                    '@' -> {
                        initialState[y to x*2] = '@'
                        pos = y to x*2
                    }
                    else -> {}
                }
            }
        }
        printMap(initialState, width, height)

        var nextState = initialState.toMap()
        var nextPos: Pair<Int, Int> = pos
        for (move in moves) {
            val next = push2(nextPos.first, nextPos.second, move, nextState) ?: continue
            nextPos = next.first
            nextState = next.second
        }
        printMap(nextState, width, height)
        println(checksum2(nextState))
    }

    part2()
}

fun push2(y: Int, x:Int , dir: Char, state: Map<Pair<Int,Int>, Char>): Pair<Pair<Int,Int>, Map<Pair<Int,Int>, Char>>? {
    val (nextY, nextX) = when (dir) {
        '>' -> y to x+1
        '<' -> y to x-1
        '^' -> y-1 to x
        'v' -> y+1 to x
        else -> throw Exception("Invalid direction $dir")
    }
    if ((dir == 'v' || dir == '^') && (state[nextY to nextX] == '[' || state[nextY to nextX] == ']')) {
        return when (state[nextY to nextX]) {
            '[' -> {
                val (_, nextStateLeft) = push2(nextY, nextX, dir, state) ?: return null
                val (_, nextState) = push2(nextY, nextX + 1, dir, nextStateLeft) ?: return null

                when (nextState[nextY to nextX]) {
                    null -> Pair(
                        nextY to nextX,
                        nextState.minus(Pair(y, x)).plus(Pair(Pair(nextY, nextX), nextState[y to x]!!))
                    )

                    else -> null
                }
            }

            ']' -> {
                val (_, nextStateRight) = push2(nextY, nextX, dir, state) ?: return null
                val (_, nextState) = push2(nextY, nextX - 1, dir, nextStateRight) ?: return null

                when (nextState[nextY to nextX]) {
                    null -> Pair(
                        nextY to nextX,
                        nextState.minus(Pair(y, x)).plus(Pair(Pair(nextY, nextX), nextState[y to x]!!))
                    )

                    else -> null
                }
            }

            else -> throw Exception("Invalid state[$nextY to $nextX] == ${state[nextY to nextX]}")
        }
    }
    return push(y, x, dir, state)
}

fun push(y: Int, x:Int , dir: Char, state: Map<Pair<Int,Int>, Char>): Pair<Pair<Int,Int>, Map<Pair<Int,Int>, Char>>? {
    val (nextY, nextX) = when (dir) {
        '>' -> y to x+1
        '<' -> y to x-1
        '^' -> y-1 to x
        'v' -> y+1 to x
        else -> throw Exception("Invalid direction $dir")
    }
    return when (state[nextY to nextX]) {
        'O', '[', ']' -> {
            val (_, nextState) = push(nextY, nextX, dir, state) ?: return null
            when (nextState[nextY to nextX]) {
                null -> Pair(
                    nextY to nextX,
                    nextState.minus(Pair(y, x)).plus(Pair(Pair(nextY, nextX), nextState[y to x]!!))
                )

                else -> null
            }
        }

        '#' -> null
        null -> Pair(nextY to nextX, state.minus(Pair(y, x)).plus(Pair(Pair(nextY, nextX), state[y to x]!!)))
        else -> throw Exception("Invalid state[$nextY to $nextX] == ${state[nextY to nextX]}")
    }
}

fun printMap(state: Map<Pair<Int,Int>, Char>, width: Int, height: Int) {
    val map = Array(height) {Array(width) {'.'}}
    state.forEach { (y,x), c ->
        map[y][x] = c
    }
    map.forEach { println(it.joinToString("")) }
}

fun checksum1(state: Map<Pair<Int,Int>, Char>): Int {
    return state
        .filter { it.value == 'O' }
        .keys
        .sumOf { (y, x) -> 100 * y + x }
}

fun checksum2(state: Map<Pair<Int,Int>, Char>): Int {
    return state
        .filter { it.value == '[' }
        .keys
        .sumOf { (y, x) -> 100 * y + x }
}