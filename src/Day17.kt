import java.math.BigInteger
import java.security.MessageDigest

fun main() {
	class Step(val x: Int, val y: Int, val path: String)
	val open = setOf('b', 'c', 'd', 'e', 'f')
	val dir = listOf('U', 'D', 'L', 'R')
	val dx = listOf(0, 0, -1, 1)
	val dy = listOf(-1, 1, 0, 0)
	fun md5(s: String) = BigInteger(1, MessageDigest.getInstance("MD5").digest(s.toByteArray()))
		.toString(16).padStart(32, '0')

	fun getNeighbors(step: Step, passcode: String): List<Step> {
		val h = md5(passcode + step.path)
		val neighbors = mutableListOf<Step>()
		for (i in 0 until 4) {
			if (h[i] !in open) continue
			val x = step.x + dx[i]
			val y = step.y + dy[i]
			if (x < 0 || y < 0 || x > 3 || y > 3) continue
			neighbors.add(Step(x, y, step.path + dir[i]))
		}
		return neighbors
	}

	fun part1(input: List<String>): String {
		val passcode = input[0]
		val queue = mutableListOf<Step>()
		val start = Step(0, 0, "")
		queue.add(start)
		while (queue.isNotEmpty()) {
			val curr = queue.removeFirst()
			if (curr.x == 3 && curr.y == 3) return curr.path
			for (next in getNeighbors(curr, passcode)) {
				queue.add(next)
			}
		}
		error("Path not found")
	}

	fun part2(input: List<String>): Int {
		val passcode = input[0]
		var longestPath = 0
		val queue = mutableListOf<Step>()
		val start = Step(0, 0, "")
		queue.add(start)
		while (queue.isNotEmpty()) {
			val curr = queue.removeFirst()
			if (curr.x == 3 && curr.y == 3) {
				if (longestPath < curr.path.length) longestPath = curr.path.length
				continue
			}
			for (next in getNeighbors(curr, passcode)) {
				queue.add(next)
			}
		}
		return longestPath
	}

	val testInput = readInput("Day17_test")
	val testInput2 = readInput("Day17_test2")
	val testInput3 = readInput("Day17_test3")
	check(part1(testInput) == "DDRRRD")
	check(part1(testInput2) == "DDUDRLRRUDRD")
	check(part1(testInput3) == "DRURDRUDDLLDLUURRDULRLDUUDDDRR")
	check(part2(testInput) == 370)
	check(part2(testInput2) == 492)
	check(part2(testInput3) == 830)

	val input = readInput("Day17")
	part1(input).println()
	part2(input).println()
}
