import java.math.BigInteger
import java.security.MessageDigest

fun main() {
	fun md5(s: String) = BigInteger(1, MessageDigest.getInstance("MD5").digest(s.toByteArray()))
		.toString(16).padStart(32, '0')

	fun part1(input: List<String>): String {
		val password = StringBuilder()
		for (i in 0 .. Int.MAX_VALUE) {
			val hash = md5(input[0] + i)
			if (hash.startsWith("00000")) {
				password.append(hash[5])
				if (password.length == 8) return password.toString()
			}
		}
		error("Password not found")
	}

	fun part2(input: List<String>): String {
		val password = StringBuilder("________")
		for (i in 0 .. Int.MAX_VALUE) {
			val hash = md5(input[0] + i)
			if (hash.startsWith("00000") && hash[5].isDigit()) {
				val n = hash[5].digitToInt()
				if (n < 8 && password[n] == '_') {
					password[n] = hash[6]
					if ('_' !in password) return password.toString()
				}
			}
		}
		error("Password not found")
	}

	val testInput = readInput("Day05_test")
	check(part1(testInput) == "18f47a30")
	check(part2(testInput) == "05ace8e3")

	val input = readInput("Day05")
	part1(input).println()
	part2(input).println()
}
