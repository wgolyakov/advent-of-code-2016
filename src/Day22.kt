fun main() {
	class Node(val x: Int, val y: Int, val size: Int, var used: Int, var avail: Int, var steps: Int = -1) {
		override fun toString() = "$used/$avail"
	}

	fun parse(input: List<String>): List<Node> {
		return input.drop(2).map { line ->
			val (x, y, size, used, avail) =
				Regex("/dev/grid/node-x(\\d+)-y(\\d+) +(\\d+)T +(\\d+)T +(\\d+)T +\\d+%")
					.matchEntire(line)!!.groupValues.drop(1).map { it.toInt() }
			Node(x, y, size, used, avail)
		}
	}

	fun viable(a: Node, b: Node) = a.used != 0 && a.used <= b.avail

	fun getNeighbors(node: Node, grid: List<MutableList<Node>>): List<Node> {
		return listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
			.map { node.x + it.first to node.y + it.second }
			.filter { it.first >= 0 && it.second >= 0 && it.first < grid[0].size && it.second < grid.size }
			.map { grid[it.second][it.first] }
	}

	fun countStepsToFree(node: Node, grid: List<MutableList<Node>>, path: Set<Node>): Int {
		if (node.steps != -1) return node.steps
		if (node.used == 0) {
			node.steps = 0
			return 0
		}
		val neighbors = getNeighbors(node, grid).filter { it !in path }.filter { node.used <= it.size }
		var minSteps = Int.MAX_VALUE
		for (next in neighbors) {
			if (node.used <= next.avail) {
				node.steps = 1
				next.steps = 0
				return 1
			}
			val steps = countStepsToFree(next, grid, path + node)
			if (steps < minSteps) minSteps = steps
		}
		if (minSteps < Int.MAX_VALUE) minSteps++
		node.steps = minSteps
		return node.steps
	}

	fun move(n1: Node, n2: Node) {
		n2.used += n1.used
		n2.avail -= n1.used
		n1.avail += n1.used
		n1.used = 0
	}

	fun freeNode(node: Node, grid: List<MutableList<Node>>, path: Set<Node>) {
		val next = getNeighbors(node, grid).filter { it !in path }.minBy { it.steps }
		if (node.used > next.avail)	{
			freeNode(next, grid, path + node)
		}
		move(node, next)
	}

	fun clearSteps(grid: List<MutableList<Node>>) {
		for (y in grid.indices) {
			for (x in grid[y].indices) {
				grid[y][x].steps = -1
			}
		}
	}

	fun part1(input: List<String>): Int {
		val nodes = parse(input)
		var viableCount = 0
		for ((i, n1) in nodes.withIndex()) {
			for (j in i + 1 until nodes.size) {
				val n2 = nodes[j]
				if (viable(n1, n2)) viableCount++
				if (viable(n2, n1)) viableCount++
			}
		}
		return viableCount
	}

	fun part2(input: List<String>): Int {
		val nodes = parse(input)
		val grid = mutableListOf<MutableList<Node>>()
		for (node in nodes) {
			if (node.y >= grid.size) grid.add(mutableListOf())
			grid[node.y].add(node)
		}
		var steps = 0
		for ((n1, n2) in grid[0].reversed().windowed(2)) {
			if (n1.used <= n2.avail) {
				move(n1, n2)
				steps++
				continue
			}
			clearSteps(grid)
			steps += countStepsToFree(n2, grid, setOf(n1))
			freeNode(n2, grid, setOf(n1))
			move(n1, n2)
			steps++
		}
		return steps
	}

	val testInput = readInput("Day22_test")
	check(part2(testInput) == 7)

	val input = readInput("Day22")
	part1(input).println()
	part2(input).println()
}
