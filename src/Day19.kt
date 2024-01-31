fun main() {
	fun part1(input: List<String>): Int {
		val n = input[0].toInt()
		var step = 1
		val presents = IntArray(n) { 1 }
		var i = 0
		while (step < n) {
			val nextI = (i + step) % n
			step = presents[nextI]
			presents[i] += presents[nextI]
			presents[nextI] = 0
			i = (nextI + step) % n
			step = presents[i]
		}
		return presents.indexOf(n) + 1
	}

	fun part2(input: List<String>): Int {
		val n = input[0].toInt()
		val elves = MutableList(n) { it + 1 }
		var i = 0
		while (elves.size > 1) {
			val across = (i + elves.size / 2) % elves.size
			elves.removeAt(across)
			if (i < across) i++
			if (i >= elves.size) i -= elves.size
		}
		return elves.single()
	}

	val testInput = readInput("Day19_test")
	check(part1(testInput) == 3)
	check(part2(testInput) == 2)

	val input = readInput("Day19")
	part1(input).println()
	part2(input).println()
}
