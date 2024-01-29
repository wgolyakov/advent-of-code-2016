fun main() {
	class Disc(val num: Int, val positions: Int, val start: Int) {
		fun state(t0: Int) = (start + t0 + num) % positions
	}

	fun parse(input: List<String>): List<Disc> {
		return input.map { line ->
			val (num, positions, start) =
				Regex("Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+).")
					.matchEntire(line)!!.groupValues.drop(1).map { it.toInt() }
			Disc(num, positions, start)
		}
	}

	fun canGetCapsule(t0: Int, discs: List<Disc>) = discs.all { it.state(t0) == 0 }

	fun part1(input: List<String>): Int {
		val discs = parse(input)
		return (0 .. Int.MAX_VALUE).first { canGetCapsule(it, discs) }
	}

	fun part2(input: List<String>): Int {
		val discs = parse(input) + Disc(input.size + 1, 11, 0)
		return (0 .. Int.MAX_VALUE).first { canGetCapsule(it, discs) }
	}

	val testInput = readInput("Day15_test")
	check(part1(testInput) == 5)

	val input = readInput("Day15")
	part1(input).println()
	part2(input).println()
}
