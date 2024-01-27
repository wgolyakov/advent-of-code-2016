fun main() {
	fun fillValues(input: List<String>): Map<Int, Set<Int>> {
		val bots = mutableMapOf<Int, List<Int>>()
		val values = mutableMapOf<Int, MutableSet<Int>>()
		for (line in input) {
			if (line.startsWith("value ")) {
				val (value, bot) = line.substringAfter("value ")
					.split(" goes to bot ").map { it.toInt() }
				values.getOrPut(bot) { mutableSetOf() }.add(value)
			} else {
				val (bot, lowType, low, highType, high) =
					Regex("bot (\\d+) gives low to (bot|output) (\\d+) and high to (bot|output) (\\d+)")
						.matchEntire(line)!!.groupValues.drop(1)
				var lowBot = low.toInt()
				var highBot = high.toInt()
				if (lowType == "output") lowBot = -(lowBot + 1)
				if (highType == "output") highBot = -(highBot + 1)
				bots[bot.toInt()] = listOf(lowBot, highBot)
			}
		}
		val queue = bots.keys.toMutableList()
		while (queue.isNotEmpty()) {
			val bot = queue.removeFirst()
			val chips = values[bot]
			if (chips != null && chips.size == 2) {
				val (lowVal, highVal) = chips.sorted()
				val (low, high) = bots[bot]!!
				values.getOrPut(low) { mutableSetOf() }.add(lowVal)
				values.getOrPut(high) { mutableSetOf() }.add(highVal)
			} else {
				queue.add(bot)
			}
		}
		return values
	}

	fun part1(input: List<String>, comparing: Set<Int>): Int {
		val values = fillValues(input)
		return values.entries.find { it.value == comparing }!!.key
	}

	fun part2(input: List<String>): Int {
		val values = fillValues(input)
		return listOf(0, 1, 2).map { values[-(it + 1)]!!.single() }.reduce(Int::times)
	}

	val testInput = readInput("Day10_test")
	check(part1(testInput, setOf(5, 2)) == 2)
	check(part2(testInput) == 5 * 2 * 3)

	val input = readInput("Day10")
	part1(input, setOf(61, 17)).println()
	part2(input).println()
}
