fun main() {
	fun run(input: List<String>, reg: MutableMap<String, Int>): String {
		val result = StringBuilder()
		var i = 0
		while (i < input.size) {
			val line = input[i]
			val instruction = line.substring(0, 3)
			val arg = line.drop(4)
			when (instruction) {
				"cpy" -> {
					val (x, y) = arg.split(' ')
					reg[y] = if (x[0].isLetter()) reg[x] ?: 0 else x.toInt()
				}
				"inc" -> reg[arg] = (reg[arg] ?: 0) + 1
				"dec" -> reg[arg] = (reg[arg] ?: 0) - 1
				"jnz" -> {
					val (x, y) = arg.split(' ')
					val xVal = if (x[0].isLetter()) reg[x] ?: 0 else x.toInt()
					if (xVal != 0) i += y.toInt() - 1
				}
				"out" -> {
					val x = if (arg[0].isLetter()) reg[arg] ?: 0 else arg.toInt()
					result.append(x)
					if (result.length >= 10) break
				}
				else -> error("Unknown instruction: $instruction")
			}
			i++
		}
		return result.toString()
	}

	fun part1(input: List<String>): Int {
		val pattern = "0101010101"
		for (i in 0 until Int.MAX_VALUE) {
			val signal = run(input, mutableMapOf("a" to i))
			if (signal == pattern) return i
		}
		return -1
	}

	val input = readInput("Day25")
	part1(input).println()
}
