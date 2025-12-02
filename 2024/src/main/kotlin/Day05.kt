package org.example

import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val inputLines = File("src/main/resources/day05.txt").readLines()

    println("Execution time " + measureTimeMillis {
        // Map of all rules:
        // Where rules[pageNum] is a set of all number which must appear after pageNum
        val rules = inputLines
            .takeWhile { it.isNotEmpty() }
            .map {
                it.split('|').map(Integer::valueOf)
            }
            .map { hashMapOf(Pair(it[0], mutableSetOf(it[1]))) }
            .reduce { acc, hashMap ->
                val key = hashMap.keys.first()
                val value = hashMap[key]!!
                acc.getOrPut(key) { value }.addAll(value)
                acc
            }
        // List of all updates
        // Each update is a List<Int> of page numbers
        val updates = inputLines
            .dropWhile { it.isEmpty() || it.contains('|') }
            .map { it.split(',').map { intStr -> Integer.valueOf(intStr) } }

        // Map each update to:
        // For each update, the resulting map looks like this:
        // map[pageNum] set of all numbers appearing before pageNum
        val updatesMapping = updates.map { updateList ->
            updateList.mapIndexed { index, pageNum ->
                hashMapOf(Pair(pageNum, updateList.subList(0, index).toHashSet()))
            }.reduce { acc, hashMap ->
                acc.putAll(hashMap)
                acc
            }
        }

        println("Part One:")
        // For each update:
        // For each pageNum, check if there is at least on number before "pageNum" that must appear after "pagenum"
        val result1 = updatesMapping
            .map { mapping ->
                mapping.all { (num, befores) ->
                    rules[num]?.any { it in befores } != true
                }
            }
            .zip(updates)
        // filter all valid updates and sum
        val summedResult1 = result1
            .filter { (result, _) -> result }
            .map { (_, updates) ->
                updates[updates.size / 2]
            }
            .reduce { acc, i -> acc + i }
        println(summedResult1)


        println("Part Two:")
        // filter all invalid updates
        // correct them by adding each update, pageNum by pageNum into a binary tree and flattening afterward
        val result2 = result1
            .filter { (valid, _) -> !valid }
            .map { (_, updates) ->
                val tree = BinaryTree(rules)
                updates.forEach { tree.insert(it) }
                tree.flatten()
            }
        // sum up
        val summedResult2 = result2
            .map {
                it[it.size / 2]
            }
            .reduce { acc, i -> acc + i }

        println(summedResult2)
    } + "ms")
}

class BinaryTree(val rules: HashMap<Int, MutableSet<Int>>) {
    private var root: TreeNode? = null

    fun insert(num: Int) {
        // Set root if not present
        if (root == null) {
            root = TreeNode(num)
            return
        }
        root!!.insert(num, rules)
    }

    fun flatten(): List<Int> {
        if (root == null) {
            return emptyList()
        }
        return root!!.flatten()
    }

    class TreeNode(val value: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null

        fun insert(newVal: Int, rules: HashMap<Int, MutableSet<Int>>) {
            if (rules[newVal]?.contains(value) != false) {
                if (left == null) {
                    left = TreeNode(newVal)
                    return
                } else
                    left!!.insert(newVal, rules)
            } else {
                if (right == null) {
                    right = TreeNode(newVal)
                    return
                } else
                    right!!.insert(newVal, rules)
            }
        }

        fun flatten(): List<Int> {
            val leftList = if (left == null) listOf() else left!!.flatten()
            val rightList = if (right == null) listOf() else right!!.flatten()

            return leftList + listOf(this.value) + rightList
        }
    }
}