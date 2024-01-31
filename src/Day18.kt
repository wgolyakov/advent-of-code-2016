fun main() {
	val traps = setOf("^^.", ".^^", "^..", "..^")

	fun part1(input: List<String>, rows: Int): Int {
		val grid = mutableListOf(input[0])
		for (y in 1 until rows) {
			val prev = "." + grid[y - 1] + "."
			val row = StringBuilder()
			for (t in prev.windowed(3)) {
				val c = if (t in traps) '^' else '.'
				row.append(c)
			}
			grid.add(row.toString())
		}
		return grid.sumOf { row -> row.count { it == '.' } }
	}

	fun part2(input: List<String>, rows: Int): Int {
		var prev = StringBuilder(input[0])
		var count = prev.count { it == '.' }
		for (y in 1 until rows) {
			val row = StringBuilder()
			for (t in ".$prev.".windowed(3)) {
				val c = if (t in traps) '^' else '.'
				row.append(c)
			}
			count += row.count { it == '.' }
			prev = row
		}
		return count
	}

	val testInput = readInput("Day18_test")
	check(part1(testInput, 10) == 38)

	val input = readInput("Day18")
	part1(input, 40).println()
	part2(input, 400000).println()
}
