import java.math.BigInteger
import java.security.MessageDigest

fun main() {
	fun md5(s: String) = BigInteger(1, MessageDigest.getInstance("MD5").digest(s.toByteArray()))
		.toString(16).padStart(32, '0')
	fun findThree(s: String) = s.windowed(3).find { it[0] == it[1] && it[1] == it[2] }
	fun containsFive(s: String, c: Char) = "$c".repeat(5) in s

	fun md5Stretched(s: String): String {
		var h = s
		for (i in 0 .. 2016) h = md5(h)
		return h
	}

	fun find64KeyIndex(salt: String, hashFunction: (String) -> String): Int {
		val cache = mutableMapOf<String, String>()
		var i = 0
		var keyCount = 0
		while (true) {
			val h3 = cache.getOrPut(salt + i) { hashFunction(salt + i) }
			i++
			val three = findThree(h3) ?: continue
			for (j in i until i + 1000) {
				val h5 = cache.getOrPut(salt + j) { hashFunction(salt + j) }
				if (!containsFive(h5, three[0])) continue
				keyCount++
				if (keyCount == 64) return i - 1
				break
			}
		}
	}

	fun part1(input: List<String>): Int {
		return find64KeyIndex(input[0], ::md5)
	}

	fun part2(input: List<String>): Int {
		return find64KeyIndex(input[0], ::md5Stretched)
	}

	val testInput = readInput("Day14_test")
	check(part1(testInput) == 22728)
	check(part2(testInput) == 22551)

	val input = readInput("Day14")
	part1(input).println()
	part2(input).println()
}
