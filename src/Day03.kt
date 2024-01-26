fun main() {
	fun parse(line: String) = line.trim().split(" +".toRegex()).map { it.toInt() }

	fun possible(a: Int, b: Int, c: Int) = a + b > c && a + c > b && b + c > a

	fun part1(input: List<String>): Int {
		return input.count {
			val (a, b, c) = parse(it)
			possible(a, b, c)
		}
	}

	fun part2(input: List<String>): Int {
		var count = 0
		for ((line1, line2, line3) in input.windowed(3, 3)) {
			val (a1, a2, a3) = parse(line1)
			val (b1, b2, b3) = parse(line2)
			val (c1, c2, c3) = parse(line3)
			if (possible(a1, b1, c1)) count++
			if (possible(a2, b2, c2)) count++
			if (possible(a3, b3, c3)) count++
		}
		return count
	}

	val input = readInput("Day03")
	part1(input).println()
	part2(input).println()
}
