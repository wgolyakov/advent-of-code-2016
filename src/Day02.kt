fun main() {
	fun part1(input: List<String>): String {
		val code = StringBuilder()
		val keypad = listOf(
			"123",
			"456",
			"789"
		)
		var x = 1
		var y = 1
		for (line in input) {
			for (c in line) {
				when (c) {
					'U' -> if (y > 0) y--
					'D' -> if (y < 2) y++
					'L' -> if (x > 0) x--
					'R' -> if (x < 2) x++
					else -> error("Wrong instruction: $c")
				}
			}
			code.append(keypad[y][x])
		}
		return code.toString()
	}

	fun part2(input: List<String>): String {
		val code = StringBuilder()
		val keypad = listOf(
			"  1  ",
			" 234 ",
			"56789",
			" ABC ",
			"  D  "
		)
		var x = 0
		var y = 2
		for (line in input) {
			for (c in line) {
				when (c) {
					'U' -> if (y > 0 && keypad[y - 1][x] != ' ') y--
					'D' -> if (y < 4 && keypad[y + 1][x] != ' ') y++
					'L' -> if (x > 0 && keypad[y][x - 1] != ' ') x--
					'R' -> if (x < 4 && keypad[y][x + 1] != ' ') x++
					else -> error("Wrong instruction: $c")
				}
			}
			code.append(keypad[y][x])
		}
		return code.toString()
	}

	val testInput = readInput("Day02_test")
	check(part1(testInput) == "1985")
	check(part2(testInput) == "5DB3")

	val input = readInput("Day02")
	part1(input).println()
	part2(input).println()
}
