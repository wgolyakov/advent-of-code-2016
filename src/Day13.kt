fun main() {
	fun space(x: Int, y: Int, favNum: Int): Char {
		val a = x * x + 3 * x + 2 * x * y + y + y * y + favNum
		return if (a.countOneBits() % 2 == 0) '.' else '#'
	}

	fun getNeighbors(c: Pair<Int, Int>, grid: MutableMap<Pair<Int, Int>, Char>, favNum: Int): List<Pair<Int, Int>> {
		val (x ,y) = c
		return listOf(x + 1 to y, x - 1 to y, x to y + 1, x to y - 1).filter { (x, y) ->
			x >= 0 && y >= 0 && grid.getOrPut(x to y) { space(x, y, favNum) } == '.'
		}
	}

	fun part1(input: List<String>, end: Pair<Int, Int>): Int {
		val favNum = input[0].toInt()
		val grid = mutableMapOf<Pair<Int, Int>, Char>()
		val dist = mutableMapOf<Pair<Int, Int>, Int>()
		val queue = mutableListOf<Pair<Int, Int>>()
		val start = 1 to 1
		dist[start] = 0
		queue.add(start)
		while (queue.isNotEmpty()) {
			val curr = queue.removeFirst()
			val distance = dist[curr]!!
			if (curr == end) return distance
			for (next in getNeighbors(curr, grid, favNum)) {
				if (next !in dist) {
					dist[next] = distance + 1
					queue.add(next)
				}
			}
		}
		return -1
	}

	fun part2(input: List<String>): Int {
		val favNum = input[0].toInt()
		val grid = mutableMapOf<Pair<Int, Int>, Char>()
		val dist = mutableMapOf<Pair<Int, Int>, Int>()
		val queue = mutableListOf<Pair<Int, Int>>()
		val start = 1 to 1
		dist[start] = 0
		queue.add(start)
		while (queue.isNotEmpty()) {
			val curr = queue.removeFirst()
			val distance = dist[curr]!!
			if (distance == 50) continue
			for (next in getNeighbors(curr, grid, favNum)) {
				if (next !in dist) {
					dist[next] = distance + 1
					queue.add(next)
				}
			}
		}
		return dist.size
	}

	val testInput = readInput("Day13_test")
	check(part1(testInput, 7 to 4) == 11)

	val input = readInput("Day13")
	part1(input, 31 to 39).println()
	part2(input).println()
}
