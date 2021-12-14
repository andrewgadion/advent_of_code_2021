typealias Paper = List<Pair<Int, Int>>
typealias Instruction = Pair<String, Int>

fun main() {

    fun fold(paper: Paper, instruction: Instruction): Paper {
        fun Int.mirror(byValue: Int) = if (this < byValue) this else byValue - (this - byValue)
        return paper.map {
            val (axis, value) = instruction
            when (axis) {
                "x" -> it.first.mirror(value) to it.second
                "y" -> it.first to it.second.mirror(value)
                else -> throw Exception("Illegal instruction")
            }
        }.distinct()
    }

    fun parseInput(input: List<String>) : Pair<Paper, List<Instruction>> {
        val paperInput = input.takeWhile { it.isNotBlank() }
        val paper = paperInput.map { it.toIntList() }.map { it[0] to it[1] }
        val instructions = input
            .subList(paperInput.size + 1, input.size)
            .map { it.split(" ").last().split("=") }.map { it[0] to it[1].toInt() }
        return paper to instructions
    }

    fun part1(input: List<String>): Int {
        val (paper, instructions) = parseInput(input)
        return fold(paper, instructions.first()).size
    }

    fun part2(input: List<String>): String {
        val (paper, instructions) = parseInput(input)
        val result = instructions.fold(paper) { acc, instruction -> fold(acc, instruction) }
        val display = Array(result.maxOf { it.second } + 1) {
            Array(result.maxOf { it.first } + 1) { ' ' }
        }
        result.forEach {
            display[it.second][it.first] = '#'
        }
        return display.joinToString("\n") { it.joinToString("")  }
    }

    val input = readInput("day13")
    println(part1(input))
    println(part2(input))
}
