fun main() {
	data class Pair(val generatorFloor: Int, val chipFloor: Int)

	data class State(val elevator: Int, val pairs: Map<Pair, Int>) {
		fun final() = pairs.size == 1 && Pair(3, 3) in pairs

		private fun bad() = pairs.keys.any { pair -> pair.generatorFloor != pair.chipFloor &&
				pairs.keys.any { it.generatorFloor == pair.chipFloor } }

		private fun movePair(pair: Pair, newPair: Pair, floor: Int): State {
			val newPairs = pairs.toMutableMap()
			val num = newPairs[pair]!!
			if (num > 1) {
				newPairs[pair] = num - 1
			} else {
				newPairs.remove(pair)
			}
			newPairs[newPair] = newPairs.getOrDefault(newPair, 0) + 1
			return State(floor, newPairs)
		}

		private fun moveGenerator(pair: Pair, floor: Int) = movePair(pair, Pair(floor, pair.chipFloor), floor)

		private fun moveChip(pair: Pair, floor: Int) = movePair(pair, Pair(pair.generatorFloor, floor), floor)

		fun addPair(pair: Pair): State {
			val newPairs = pairs.toMutableMap()
			newPairs[pair] = newPairs.getOrDefault(pair, 0) + 1
			return State(elevator, newPairs)
		}

		fun nextStates(): List<State> {
			val states = mutableListOf<State>()
			val currFloorGenerators = pairs.keys.filter { it.generatorFloor == elevator }
			val currFloorChips = pairs.keys.filter { it.chipFloor == elevator }
			for (f in listOf(elevator + 1, elevator - 1)) {
				if (f < 0 || f > 3) continue
				for ((i1, g1) in currFloorGenerators.withIndex()) {
					val st = moveGenerator(g1, f)
					states.add(st)
					if (g1 in st.pairs) states.add(st.moveGenerator(g1, f))
					for (i2 in i1 + 1 until currFloorGenerators.size) {
						val g2 = currFloorGenerators[i2]
						states.add(st.moveGenerator(g2, f))
					}
					for (c2 in currFloorChips) {
						if (c2 != g1) continue
						states.add(movePair(g1, Pair(f, f), f))
					}
				}
				for ((i1, c1) in currFloorChips.withIndex()) {
					val st = moveChip(c1, f)
					states.add(st)
					if (c1 in st.pairs) states.add(st.moveChip(c1, f))
					for (i2 in i1 + 1 until currFloorChips.size) {
						val c2 = currFloorChips[i2]
						states.add(st.moveChip(c2, f))
					}
				}
			}
			return states.filter { !it.bad() }
		}
	}

	fun parse(input: List<String>): State {
		val generators = mutableMapOf<String, Int>()
		val chips = mutableMapOf<String, Int>()
		for ((floor, line) in input.withIndex()) {
			val s = line.substringAfter("floor contains ")
			if (s == "nothing relevant.") continue
			for (str in s.dropLast(1).split(", and |, | and ".toRegex())) {
				if (str.endsWith("-compatible microchip")) {
					val type = str.substringBefore("-compatible microchip").substringAfter("a ")
					chips[type] = floor
				} else if (str.endsWith(" generator")) {
					val type = str.substringBefore(" generator").substringAfter("a ")
					generators[type] = floor
				}
			}
		}
		val pairs = mutableMapOf<Pair, Int>()
		val genFloors = generators.entries.sortedBy { it.key }.map { it.value }
		val chipFloors = chips.entries.sortedBy { it.key }.map { it.value }
		for (i in genFloors.indices) {
			val pair = Pair(genFloors[i], chipFloors[i])
			pairs[pair] = pairs.getOrDefault(pair, 0) + 1
		}
		return State(0, pairs)
	}

	fun countSteps(initState: State): Int {
		val visited = mutableSetOf<State>()
		var steps = 0
		val nextQueue = mutableListOf(initState)
		while (nextQueue.isNotEmpty()) {
			val queue = nextQueue.toMutableList()
			nextQueue.clear()
			while (queue.isNotEmpty()) {
				val state = queue.removeFirst()
				if (state.final()) return steps
				if (state in visited) continue
				nextQueue.addAll(state.nextStates())
				visited.add(state)
			}
			steps++
		}
		return -1
	}

	fun part1(input: List<String>): Int {
		val initState = parse(input)
		return countSteps(initState)
	}

	fun part2(input: List<String>): Int {
		val state = parse(input)
		val initState = state.addPair(Pair(0, 0)).addPair(Pair(0, 0))
		return countSteps(initState)
	}

	val testInput = readInput("Day11_test")
	check(part1(testInput) == 11)

	val input = readInput("Day11")
	part1(input).println()
	part2(input).println()
}
