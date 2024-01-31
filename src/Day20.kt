fun main() {
	fun contains(x: Long, ranges: List<LongRange>) = ranges.any { x in it }

	fun union(ranges: List<LongRange>): List<LongRange> {
		val result = mutableListOf<LongRange>()
		val list = ranges.flatMap { listOf(it.first, it.last) }.flatMap { listOf(it - 1, it, it + 1) }.toSet().sorted()
		var a: Long? = null
		var b: Long? = null
		for (x in list) {
			if (contains(x, ranges)) {
				if (a == null) {
					a = x
				} else {
					b = x
				}
			} else {
				if (a != null) {
					if (b != null) {
						result.add(a .. b)
						b = null
					} else {
						result.add(a .. a)
					}
					a = null
				}
			}
		}
		return result
	}

	fun part1(input: List<String>): Long {
		val blackList = input.map { it.split('-') }.map { it[0].toLong() .. it[1].toLong() }
			.sortedBy { it.first }
		if (blackList.first().first > 0) return 0
		for (a in blackList) {
			val x = a.last + 1
			if (!contains(x, blackList)) return x
		}
		return -1
	}

	fun part2(input: List<String>): Long {
		val blackList = input.map { it.split('-') }.map { it[0].toLong() .. it[1].toLong() }
		val blockedCount = union(blackList).sumOf { it.last - it.first + 1 }
		return 4294967295L + 1 - blockedCount
	}

	val testInput = readInput("Day20_test")
	check(part1(testInput) == 3L)
	check(part2(testInput) == 4294967295L + 1 - 8)

	val input = readInput("Day20")
	part1(input).println()
	part2(input).println()
}
