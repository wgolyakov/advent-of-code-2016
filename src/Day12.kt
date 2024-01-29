fun main() {
	fun run(input: List<String>, reg: MutableMap<String, Int> = mutableMapOf()): Int {
		var i = 0
		while (i < input.size) {
			val line = input[i]
			val instruction = line.substring(0, 3)
			val arg = line.drop(4)
			when (instruction) {
				"cpy" -> {
					val (x, y) = arg.split(' ')
					reg[y] = if (x[0].isDigit()) x.toInt() else reg[x] ?: 0
				}
				"inc" -> reg[arg] = (reg[arg] ?: 0) + 1
				"dec" -> reg[arg] = (reg[arg] ?: 0) - 1
				"jnz" -> {
					val (x, y) = arg.split(' ')
					val xVal = if (x[0].isDigit()) x.toInt() else reg[x] ?: 0
					if (xVal != 0) i += y.toInt() - 1
				}
				else -> error("Unknown instruction: $instruction")
			}
			i++
		}
		return reg["a"]!!
	}

	fun part1(input: List<String>): Int {
		return run(input)
	}

	fun part2(input: List<String>): Int {
		return run(input, mutableMapOf("c" to 1))
	}

	val testInput = readInput("Day12_test")
	check(part1(testInput) == 42)

	val input = readInput("Day12")
	part1(input).println()
	part2(input).println()
}
