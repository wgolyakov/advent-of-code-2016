fun main() {
	class Room(val encryptedName: String, val sectorId: Int, val checksum: String) {
		fun real(): Boolean {
			val counts = mutableMapOf<Char, Int>()
			for (c in encryptedName) {
				if (c == '-') continue
				counts[c] = counts.getOrDefault(c, 0) + 1
			}
			val cs = counts.asSequence().sortedByDescending { it.value * 100 + 'z'.code - it.key.code }
				.take(5).map { it.key }.joinToString("")
			return cs == checksum
		}

		fun name(): String {
			val result = StringBuilder()
			for (c in encryptedName) {
				if (c == '-') {
					result.append(' ')
				} else {
					var r = c + (sectorId % 26)
					if (r > 'z') r -= 26
					result.append(r)
				}
			}
			return result.toString()
		}
	}

	fun parse(input: List<String>): List<Room> {
		return input.map { line ->
			val (s, checksum) = line.dropLast(1).split('[')
			val encryptedName = s.substringBeforeLast('-')
			val sectorId = s.substringAfterLast('-').toInt()
			Room(encryptedName, sectorId, checksum)
		}
	}

	fun part1(input: List<String>): Int {
		val rooms = parse(input)
		return rooms.filter { it.real() }.sumOf { it.sectorId }
	}

	fun part2(input: List<String>): Int {
		val rooms = parse(input)
		//for (room in rooms) println(room.name())
		return rooms.find { "north" in it.name() }!!.sectorId
	}

	val testInput = readInput("Day04_test")
	check(part1(testInput) == 1514)
	check(Room("qzmt-zixmtkozy-ivhz", 343, "").name() == "very encrypted name")

	val input = readInput("Day04")
	part1(input).println()
	part2(input).println()
}
