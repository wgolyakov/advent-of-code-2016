fun main() {
	fun supportsTLS(ip: String): Boolean {
		var abba = false
		var inside = false
		for (s in ip.windowed(4)) {
			if (s.last() == '[') {
				inside = true
			} else if (s.last() == ']') {
				inside = false
			} else if (s[0] == s[3] && s[1] == s[2] && s[0] != s[1]) {
				if (inside) return false else abba = true
			}
		}
		return abba
	}

	fun supportsSSL(ip: String): Boolean {
		val outside = mutableListOf<String>()
		val inside = mutableListOf<String>()
		val list = ip.split('[').map { it.split(']') }
		for (s in list) {
			if (s.size == 2) {
				inside.add(s[0])
				outside.add(s[1])
			} else {
				outside.add(s[0])
			}
		}
		val outsideABA = mutableSetOf<String>()
		val insideBAB = mutableSetOf<String>()
		for (part in outside) {
			for (s in part.windowed(3)) {
				if (s[0] == s[2] && s[0] != s[1]) outsideABA.add(s)
			}
		}
		for (part in inside) {
			for (s in part.windowed(3)) {
				if (s[0] == s[2] && s[0] != s[1]) insideBAB.add(s)
			}
		}
		for (bab in insideBAB) {
			val aba = "${bab[1]}${bab[0]}${bab[1]}"
			if (aba in outsideABA) return true
		}
		return false
	}

	fun part1(input: List<String>): Int {
		return input.count { supportsTLS(it) }
	}

	fun part2(input: List<String>): Int {
		return input.count { supportsSSL(it) }
	}

	val testInput = readInput("Day07_test")
	val testInput2 = readInput("Day07_test2")
	check(part1(testInput) == 2)
	check(part2(testInput2) == 3)

	val input = readInput("Day07")
	part1(input).println()
	part2(input).println()
}
