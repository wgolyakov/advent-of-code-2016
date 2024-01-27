fun main() {
	fun decompress(str: String): String {
		val result = StringBuilder()
		val marker = StringBuilder()
		var i = 0
		while (i < str.length) {
			val c = str[i]
			if (marker.isEmpty()) {
				if (c == '(') {
					marker.append(c)
				} else {
					result.append(c)
				}
			} else {
				if (c == ')') {
					val (len, rep) = marker.drop(1).split('x').map { it.toInt() }
					marker.clear()
					val s = str.substring(i + 1, i + 1 + len)
					for (r in 1 .. rep) result.append(s)
					i += len
				} else {
					marker.append(c)
				}
			}
			i++
		}
		return result.toString()
	}

	fun decompress2(str: String): Long {
		var result = 0L
		val marker = StringBuilder()
		var i = 0
		while (i < str.length) {
			val c = str[i]
			if (marker.isEmpty()) {
				if (c == '(') {
					marker.append(c)
				} else {
					result++
				}
			} else {
				if (c == ')') {
					val (len, rep) = marker.drop(1).split('x').map { it.toInt() }
					marker.clear()
					val s = str.substring(i + 1, i + 1 + len)
					result += decompress2(s) * rep
					i += len
				} else {
					marker.append(c)
				}
			}
			i++
		}
		return result
	}

	fun part1(input: List<String>): Int {
		return input.sumOf { decompress(it).length }
	}

	fun part2(input: List<String>): Long {
		return input.sumOf { decompress2(it) }
	}

	val testInput = readInput("Day09_test")
	val testInput2 = readInput("Day09_test2")
	check(part1(testInput) == 6 + 7 + 9 + 11 + 6 + 18)
	check(part2(testInput2) == 9L + 20 + 241920 + 445)

	val input = readInput("Day09")
	part1(input).println()
	part2(input).println()
}
