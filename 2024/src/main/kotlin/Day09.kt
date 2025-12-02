package org.example

import java.io.File

fun main() {
    val input = File("src/main/resources/day09.txt")
        .readText()
        .toCharArray()
        .toList()
        .filter { it in '0'..'9' }
        .map(Char::digitToInt)

    var isFile = true
    var fileId = 0

    val fileSys = input
        .map{ digit ->
            if (isFile) {
                isFile = false
                val list = List(digit) { fileId }
                fileId++
                return@map list
            } else {
                isFile = true
                val list = List(digit) { -1 }
                return@map list
            }
        }
        .filter { it.isNotEmpty() }

    println("Part One: ")
    val fileSys1 = fileSys
        .flatten()
        .toMutableList()
    var i1 = 0
    var j1 = fileSys1.size - 1
    while (i1 < j1) {
        if (fileSys1[i1] != -1) {
            i1++
            continue
        }
        if (fileSys1[j1] == -1) {
            j1--
            continue
        }

        fileSys1[i1] = fileSys1[j1]
        fileSys1[j1] = -1
        i1++
        j1--
    }

    val result1 = fileSys1
        .takeWhile { it != -1 }
        .mapIndexed { idx, id ->
            idx * id
        }.sumOf { it.toLong() }

    println("$result1")

    println("Part Two: ")

    val fileSys2 = fileSys.toMutableList()

    // Loop all blocks back to front
    var i2 = fileSys2.size
    while (i2 > 0) {
        i2--
        val block = fileSys2[i2]
        // skip empty blocks
        if (block[0] == -1)
            continue
        // find first suitable empty block before this one
        val idxEmptyBlock = fileSys2.subList(0, i2).indexOfFirst { b ->
            b[0] == -1 &&
                    b.size >= block.size }
        // skip if not found
        if (idxEmptyBlock == -1)
            continue
        // empty working block
        fileSys2[i2] = List(block.size) {-1}
        // split empty block leftovers in extra entry
        val sizeDiff = fileSys2[idxEmptyBlock].size - block.size
        if (sizeDiff > 0) {
            i2++
            fileSys2.add(idxEmptyBlock + 1, List(sizeDiff) { -1 })
        }
        // replace empty block with working block
        fileSys2[idxEmptyBlock] = List(block.size) { block[0] }
    }


    val result2 = fileSys2
        .flatten()
        .mapIndexed { idx, id ->
            if (id == -1) return@mapIndexed 0
            (idx * id).toLong()
        }.sum()

    println(result2)
}

