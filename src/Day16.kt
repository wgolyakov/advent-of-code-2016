fun main() {
	fun not(b: String) = b.map { if (it == '0') '1' else '0' }.joinToString("")
	fun oneStep(a: String) = a + '0' + not(a.reversed())

	fun generate(a: String, diskLength: Int): String {
		var b = a
		while (b.length < diskLength) b = oneStep(b)
		return b.substring(0, diskLength)
	}

	fun checksum(a: String): String {
		var b = a
		while (b.length % 2 == 0) b = b.windowed(2, 2)
			.map { if (it[0] == it[1]) '1' else '0' }.joinToString("")
		return b
	}

	fun part1(input: List<String>, diskLength: Int): String {
		return checksum(generate(input[0], diskLength))
	}

	fun part2(input: List<String>): String {
		return part1(input, 35651584)
	}

	check(oneStep("1") == "100")
	check(oneStep("0") == "001")
	check(oneStep("11111") == "11111000000")
	check(oneStep("111100001010") == "1111000010100101011110000")
	check(checksum("110010110100") == "100")

	val testInput = readInput("Day16_test")
	check(part1(testInput, 20) == "01100")

	val input = readInput("Day16")
	part1(input, 272).println()
	part2(input).println()
}
