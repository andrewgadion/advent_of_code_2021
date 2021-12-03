import kotlin.math.ceil

fun main()
{
    fun mostCommonBit(input: List<String>, bitIndex: Int) = input.fold(0) { acc, s ->
        if (s[bitIndex] == '1') acc + 1 else acc
    }.let { if (it >= ceil(input.count().toDouble() / 2)) 1 else 0 }

    fun part1(input: List<String>): Int
    {
        val gammaRateStr = (0 until input.first().length).map { mostCommonBit(input, it) }
        val epsilonRateStr = gammaRateStr.map { if (it == 1) 0 else 1 }
        return gammaRateStr.joinToString("").toInt(2) *
            epsilonRateStr.joinToString("").toInt(2)
    }

    fun filterOut(input: List<String>, mostCommon: Boolean): Int
    {
        val numberLength = input.first().length
        var inputLeft = input
        (0 until numberLength).forEach { bitIndex ->
            var mostCommonBit = mostCommonBit(inputLeft, bitIndex)
            if (!mostCommon)
                mostCommonBit = mostCommonBit.inv() and 1
            inputLeft = inputLeft.filter { it[bitIndex] == mostCommonBit.toString()[0] }
            if (inputLeft.size == 1)
                return inputLeft[0].toInt(2)
        }

        return -1
    }

    fun part2(input: List<String>) = filterOut(input, true) * filterOut(input, false)

    val input = readInput("day3")
    println(part1(input))
    println(part2(input))
}
