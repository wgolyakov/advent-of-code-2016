fun main() {
	fun swipe(input: List<String>): Array<StringBuilder> {
		val screen = Array(6) { StringBuilder(".".repeat(50)) }
		for (line in input) {
			if (line.startsWith("rect ")) {
				val (width, height) = line.substringAfter("rect ").split('x').map { it.toInt() }
				for (y in 0 until height) {
					for (x in 0 until width) {
						screen[y][x] = '#'
					}
				}
			} else if (line.startsWith("rotate row y=")) {
				val (y, r) = line.substringAfter("rotate row y=").split(" by ").map { it.toInt() }
				val row = screen[y].toString()
				for (x in 0 until 50) {
					val x2 = (x + r) % 50
					screen[y][x2] = row[x]
				}
			} else {
				val (x, r) = line.substringAfter("rotate column x=").split(" by ").map { it.toInt() }
				val column = StringBuilder()
				for (y in 0 until 6) {
					column.append(screen[y][x])
				}
				for (y in 0 until 6) {
					val y2 = (y + r) % 6
					screen[y2][x] = column[y]
				}
			}
		}
		return screen
	}

	fun part1(input: List<String>): Int {
		val screen = swipe(input)
		return screen.sumOf { row -> row.count { it == '#' } }
	}

	fun part2(input: List<String>): String {
		val screen = swipe(input)
		return screen.joinToString("\n")
	}

	val input = readInput("Day08")
	part1(input).println()
	part2(input).println()
}
