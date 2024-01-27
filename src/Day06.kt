fun main() {
	fun countLetters(input: List<String>): Array<MutableMap<Char, Int>> {
		val counts = Array(input[0].length) { mutableMapOf<Char, Int>() }
		for (line in input) {
			for ((i, c) in line.withIndex()) {
				val counter = counts[i]
				counter[c] = counter.getOrDefault(c, 0) + 1
			}
		}
		return counts
	}

	fun part1(input: List<String>): String {
		val counts = countLetters(input)
		return counts.map { counter -> counter.asSequence().sortedByDescending { it.value }.first().key }
			.joinToString("")
	}

	fun part2(input: List<String>): String {
		val counts = countLetters(input)
		return counts.map { counter -> counter.asSequence().sortedBy { it.value }.first().key }
			.joinToString("")
	}

	val testInput = readInput("Day06_test")
	check(part1(testInput) == "easter")
	check(part2(testInput) == "advent")

	val input = readInput("Day06")
	part1(input).println()
	part2(input).println()
}
