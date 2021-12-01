fun main() {
    fun countIncreasedNumbers(input: Sequence<Int>) = input.zip(input.drop(1)).count { it.second > it.first }

    fun part1(input: List<String>): Int {
        val inputNumbers = input
            .asSequence()
            .map { it.toInt() }
        return countIncreasedNumbers(inputNumbers)
    }

    fun part2(input: List<String>): Int {
        val inputNumbers = input.map { it.toInt() }
        val sums = (0 until input.size - 2).asSequence().map {
            inputNumbers[it] + inputNumbers[it + 1] + inputNumbers[it + 2]
        }
        return countIncreasedNumbers(sums)
    }

    val input = readInput("day1")
    println(part1(input))
    println(part2(input))
}
