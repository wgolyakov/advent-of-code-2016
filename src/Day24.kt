import kotlin.math.min

fun main() {
	data class Cell(val x: Int, val y: Int)

	fun findMarkers(map: List<String>): Map<Int, Cell> {
		val result = mutableMapOf<Int, Cell>()
		for ((y, row) in map.withIndex()) {
			for ((x, c) in row.withIndex()) {
				if (c.isDigit()) result[c.digitToInt()] = Cell(x, y)
			}
		}
		return result
	}

	fun getNeighbors(c: Cell, map: List<String>): List<Cell> {
		return listOf(Cell(c.x + 1, c.y), Cell(c.x - 1, c.y), Cell(c.x, c.y + 1), Cell(c.x, c.y - 1))
			.filter { it.x >= 0 && it.y >= 0 && it.x < map[0].length && it.y < map.size }
			.filter { map[it.y][it.x] != '#' }
	}

	fun calcDistance(m1: Int, m2: Int, map: List<String>, markers: Map<Int, Cell>): Int {
		val dist = Array(map.size) { IntArray(map[0].length) { -1 } }
		val queue = mutableListOf<Cell>()
		val start = markers[m1]!!
		val end = markers[m2]!!
		dist[start.y][start.x] = 0
		queue.add(start)
		while (queue.isNotEmpty()) {
			val curr = queue.removeFirst()
			val distance = dist[curr.y][curr.x]
			if (curr == end) return distance
			for (next in getNeighbors(curr, map)) {
				if (dist[next.y][next.x] == -1) {
					dist[next.y][next.x] = distance + 1
					queue.add(next)
				}
			}
		}
		return -1
	}

	fun calcDistances(map: List<String>, markers: Map<Int, Cell>): Map<Set<Int>, Int> {
		val result = mutableMapOf<Set<Int>, Int>()
		for (m1 in 0 until markers.size) {
			for (m2 in m1 + 1 until markers.size) {
				val d = calcDistance(m1, m2, map, markers)
				result[setOf(m1, m2)] = d
			}
		}
		return result
	}

	fun allMarksMinPath(path: List<Int>, unvisited: Set<Int>, distances: Map<Set<Int>, Int>): Int {
		if (unvisited.isEmpty()) {
			return path.windowed(2).sumOf { distances[it.toSet()]!! }
		}
		var min = Int.MAX_VALUE
		for (m in unvisited) {
			min = min(allMarksMinPath(path + m, unvisited - m, distances), min)
		}
		return min
	}

	fun allMarksAndBackMinPath(path: List<Int>, unvisited: Set<Int>, distances: Map<Set<Int>, Int>): Int {
		if (unvisited.isEmpty()) {
			return path.windowed(2).sumOf { distances[it.toSet()]!! } + distances[setOf(path.last(), 0)]!!
		}
		var min = Int.MAX_VALUE
		for (m in unvisited) {
			min = min(allMarksAndBackMinPath(path + m, unvisited - m, distances), min)
		}
		return min
	}

	fun part1(input: List<String>): Int {
		val markers = findMarkers(input)
		val distances = calcDistances(input, markers)
		return allMarksMinPath(listOf(0), markers.keys - 0, distances)
	}

	fun part2(input: List<String>): Int {
		val markers = findMarkers(input)
		val distances = calcDistances(input, markers)
		return allMarksAndBackMinPath(listOf(0), markers.keys - 0, distances)
	}

	val testInput = readInput("Day24_test")
	check(part1(testInput) == 14)

	val input = readInput("Day24")
	part1(input).println()
	part2(input).println()
}
