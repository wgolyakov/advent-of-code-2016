fun main() {
	val registers = mutableMapOf<String, Int>()

	class Argument(val name: String?, val const: Int?) {
		fun value(): Int = const ?: (registers[name!!] ?: 0)
		fun isRegister() = name != null
		fun setValue(value: Int) {
			registers[name!!] = value
		}
		override fun toString() = name ?: const?.toString() ?: ""
	}

	class Instruction(var name: String, val a: Argument, val b: Argument?) {
		operator fun get(num: Int) = if (num == 0) a.value() else b!!.value()
		operator fun set(num: Int, value: Int) {
			if (num == 0) a.setValue(value) else b!!.setValue(value)
		}
		fun isRegister(num: Int) = if (num == 0) a.isRegister() else b!!.isRegister()
		override fun toString() = "$name $a ${b ?: ""}"
	}

	fun parse(input: List<String>): List<Instruction> {
		return input.map { line ->
			val name = line.substring(0, 3)
			val arg = line.drop(4)
			val a: Argument
			val b: Argument?
			if (' ' in arg) {
				val (x, y) = arg.split(' ')
				a = if (x[0].isLetter()) Argument(x, null) else Argument(null, x.toInt())
				b = if (y[0].isLetter()) Argument(y, null) else Argument(null, y.toInt())
			} else {
				a = if (arg[0].isLetter()) Argument(arg, null) else Argument(null, arg.toInt())
				b = null
			}
			Instruction(name, a, b)
		}
	}

	fun optimizeMultiply(code: List<Instruction>, i: Int): Int {
		// cpy b c
		// inc a
		// dec c
		// jnz c -2
		// dec d
		// jnz d -5
		// ----------
		// a += b * d
		// c = 0
		// d = 0
		if (i + 5 >= code.size) return i
		val c1 = code[i]
		val c2 = code[i + 1]
		val c3 = code[i + 2]
		val c4 = code[i + 3]
		val c5 = code[i + 4]
		val c6 = code[i + 5]
		if (c1.name == "cpy" &&
			c2.name == "inc" &&
			c3.name == "dec" &&
			c4.name == "jnz" && c4.b!!.const == -2 &&
			c5.name == "dec" &&
			c6.name == "jnz" && c6.b!!.const == -5
		) {
			c2[0] += c1[0] * c6[0]
			c1[1] = 0
			c6[0] = 0
			return i + 6
		}
		return i
	}

	fun run(code: List<Instruction>, reg: MutableMap<String, Int>): Int {
		registers.clear()
		registers.putAll(reg)
		var i = 0
		while (i < code.size) {
			val instr = code[i]
			when (instr.name) {
				"cpy" -> if (instr.isRegister(1)) instr[1] = instr[0]
				"inc" -> instr[0]++
				"dec" -> instr[0]--
				"jnz" -> if (instr[0] != 0) i += instr[1] - 1
				"tgl" -> {
					val j = i + instr[0]
					if (j >= 0 && j < code.size) {
						val toggleInstr = code[j]
						toggleInstr.name = when (toggleInstr.name) {
							"cpy" -> "jnz"
							"inc" -> "dec"
							"dec" -> "inc"
							"jnz" -> "cpy"
							"tgl" -> "inc"
							else -> error("Unknown instruction: ${toggleInstr.name}")
						}
					}
				}
				else -> error("Unknown instruction: $instr")
			}
			i++
			i = optimizeMultiply(code, i)
		}
		return registers["a"]!!
	}

	fun part1(input: List<String>): Int {
		val code = parse(input)
		return run(code, mutableMapOf("a" to 7))
	}

	fun part2(input: List<String>): Int {
		val code = parse(input)
		return run(code, mutableMapOf("a" to 12))
	}

	val testInput = readInput("Day23_test")
	check(part1(testInput) == 3)

	val input = readInput("Day23")
	part1(input).println()
	part2(input).println()
}
