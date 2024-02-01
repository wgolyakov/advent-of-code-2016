fun main() {
	fun swapPosition(scr: StringBuilder, x: Int, y: Int) {
		val tmp = scr[x]
		scr[x] = scr[y]
		scr[y] = tmp
	}

	fun swapLetter(scr: StringBuilder, x: Char, y: Char) {
		val xi = scr.indexOf(x)
		val yi = scr.indexOf(y)
		val tmp = scr[xi]
		scr[xi] = scr[yi]
		scr[yi] = tmp
	}

	fun rotateLeft(scr: StringBuilder, x: Int) {
		val tmp = scr.substring(x) + scr.substring(0, x)
		scr.setRange(0, scr.length, tmp)
	}

	fun rotateRight(scr: StringBuilder, x: Int) {
		val tmp = scr.substring(scr.length - x) + scr.substring(0, scr.length - x)
		scr.setRange(0, scr.length, tmp)
	}

	fun rotateBasedRight(scr: StringBuilder, x: Char) {
		val xi = scr.indexOf(x)
		val n = (1 + xi + if (xi >= 4) 1 else 0) % scr.length
		val tmp = scr.substring(scr.length - n) + scr.substring(0, scr.length - n)
		scr.setRange(0, scr.length, tmp)
	}

	fun rotateBasedLeft(scr: StringBuilder, x: Char) {
		val xi = scr.indexOf(x)
		val n = when (xi) {
			0 -> 1
			1 -> 1
			2 -> 6
			3 -> 2
			4 -> 7
			5 -> 3
			6 -> 0
			7 -> 4
			else -> error("Works only for 8-letter password")
		}
		val tmp = scr.substring(n) + scr.substring(0, n)
		scr.setRange(0, scr.length, tmp)
	}

	fun reversePositions(scr: StringBuilder, x: Int, y: Int) {
		scr.setRange(x, y + 1, scr.substring(x, y + 1).reversed())
	}

	fun movePosition(scr: StringBuilder, x: Int, y: Int) {
		val c = scr[x]
		scr.deleteAt(x).insert(y, c)
	}

	fun part1(input: List<String>, password: String): String {
		val scr = StringBuilder(password)
		for (line in input) {
			if (line.startsWith("swap position ")) {
				val (x, y) = line.substringAfter("swap position ").split(" with position ").map { it.toInt() }
				swapPosition(scr, x, y)
			} else if (line.startsWith("swap letter ")) {
				val (x, y) = line.substringAfter("swap letter ").split(" with letter ").map { it.single() }
				swapLetter(scr, x, y)
			} else if (line.startsWith("rotate left ")) {
				val x = line.substringAfter("rotate left ").substringBefore(" step").toInt() % scr.length
				rotateLeft(scr, x)
			} else if (line.startsWith("rotate right ")) {
				val x = line.substringAfter("rotate right ").substringBefore(" step").toInt() % scr.length
				rotateRight(scr, x)
			} else if (line.startsWith("rotate based on position of letter ")) {
				val x = line.substringAfter("rotate based on position of letter ").single()
				rotateBasedRight(scr, x)
			} else if (line.startsWith("reverse positions ")) {
				val (x, y) = line.substringAfter("reverse positions ").split(" through ").map { it.toInt() }
				reversePositions(scr, x, y)
			} else if (line.startsWith("move position ")) {
				val (x, y) = line.substringAfter("move position ").split(" to position ").map { it.toInt() }
				movePosition(scr, x, y)
			} else {
				error("Unknown operation: $line")
			}
		}
		return scr.toString()
	}

	fun part2(input: List<String>, scrambled: String): String {
		val pass = StringBuilder(scrambled)
		for (line in input.reversed()) {
			if (line.startsWith("swap position ")) {
				val (x, y) = line.substringAfter("swap position ").split(" with position ").map { it.toInt() }
				swapPosition(pass, y, x)
			} else if (line.startsWith("swap letter ")) {
				val (x, y) = line.substringAfter("swap letter ").split(" with letter ").map { it.single() }
				swapLetter(pass, x, y)
			} else if (line.startsWith("rotate left ")) {
				val x = line.substringAfter("rotate left ").substringBefore(" step").toInt() % pass.length
				rotateRight(pass, x)
			} else if (line.startsWith("rotate right ")) {
				val x = line.substringAfter("rotate right ").substringBefore(" step").toInt() % pass.length
				rotateLeft(pass, x)
			} else if (line.startsWith("rotate based on position of letter ")) {
				val x = line.substringAfter("rotate based on position of letter ").single()
				rotateBasedLeft(pass, x)
			} else if (line.startsWith("reverse positions ")) {
				val (x, y) = line.substringAfter("reverse positions ").split(" through ").map { it.toInt() }
				reversePositions(pass, x, y)
			} else if (line.startsWith("move position ")) {
				val (x, y) = line.substringAfter("move position ").split(" to position ").map { it.toInt() }
				movePosition(pass, y, x)
			} else {
				error("Unknown operation: $line")
			}
		}
		return pass.toString()
	}

	val testInput = readInput("Day21_test")
	check(part1(testInput, "abcde") == "decab")

	val input = readInput("Day21")
	part1(input, "abcdefgh").println() // ghfacdbe
	check(part2(input, "ghfacdbe") == "abcdefgh")
	part2(input, "fbgdceah").println() // fhgcdaeb
}
