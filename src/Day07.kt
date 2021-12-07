import kotlin.math.abs

fun main() {

    fun List<Int>.totalDiff(value: Int): Int = this.sumOf { abs(it - value) }
    fun List<Int>.totalDiff2(value: Int): Long =
        this.sumOf { number -> abs(number - value).toLong().let { it * (it + 1) / 2 } }

    fun part1(input: List<String>): Int
    {
        val numbers = input.flatMap(String::toIntList).sorted()
        return (numbers[0]..numbers[numbers.lastIndex]).minOf { numbers.totalDiff(it) }
    }

    fun part2(input: List<String>): Long
    {
        val numbers = input.flatMap(String::toIntList).sorted()
        return (numbers[0]..numbers[numbers.lastIndex]).minOf { numbers.totalDiff2(it) }
    }

    val input = readInput("day7")
    println(part1(input))
    println(part2(input))
}
