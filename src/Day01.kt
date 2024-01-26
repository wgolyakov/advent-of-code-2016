import kotlin.math.absoluteValue

fun main() {
	fun part1(input: List<String>): Int {
		val list = input[0].split(", ")
		var x = 0
		var y = 0
		var dx = 0
		var dy = 1
		for (instruction in list) {
			val dir = instruction[0]
			val blocks = instruction.drop(1).toInt()
			if (dx == 0) {
				dx = if (dir == 'R') dy else -dy
				dy = 0
			} else {
				dy = if (dir == 'L') dx else -dx
				dx = 0
			}
			x += dx * blocks
			y += dy * blocks
		}
		return x.absoluteValue + y.absoluteValue
	}

	fun part2(input: List<String>): Int {
		val list = input[0].split(", ")
		var x = 0
		var y = 0
		var dx = 0
		var dy = 1
		val visited = mutableSetOf<Pair<Int, Int>>()
		visited.add(x to y)
		for (instruction in list) {
			val dir = instruction[0]
			val blocks = instruction.drop(1).toInt()
			if (dx == 0) {
				dx = if (dir == 'R') dy else -dy
				dy = 0
			} else {
				dy = if (dir == 'L') dx else -dx
				dx = 0
			}
			for (i in 1 .. blocks) {
				if (dx == 0) y += dy else x += dx
				if (x to y in visited) return x.absoluteValue + y.absoluteValue
				visited.add(x to y)
			}
		}
		return -1
	}

	val testInput = readInput("Day01_test")
	val testInput2 = readInput("Day01_test2")
	val testInput3 = readInput("Day01_test3")
	check(part1(testInput) == 5)
	check(part1(testInput2) == 2)
	check(part1(testInput3) == 12)
	val testInput4 = readInput("Day01_test4")
	check(part2(testInput4) == 4)

	val input = readInput("Day01")
	part1(input).println()
	part2(input).println()
}
