typealias Rules = Map<String, Char>
typealias Frequencies = Map<Char, Long>
fun main() {
    fun Frequencies.merge(other: Frequencies)
        = other.asSequence().fold(this.toMutableMap()) { acc, (key, value) ->
            acc.compute(key) { _, prev -> if (prev == null) value else prev + value }
            acc
        }

    val memo = mutableMapOf<Pair<String, Int>, Frequencies>()
    fun calculate(key: String, rules: Rules, steps: Int): Frequencies {
        if (steps == 0)
            return mapOf()

        val memoKey = key to steps
        memo[memoKey]?.let {
            return it
        }

        val charToInsert = rules[key]!!
        val res = mapOf(charToInsert to 1L)
            .merge(calculate("${key[0]}$charToInsert", rules, steps - 1))
            .merge(calculate("$charToInsert${key[1]}", rules, steps - 1))
        memo[memoKey] = res

        return res;
    }

    fun String.pairs() = this.drop(1).mapIndexed { i, c -> "${this[i]}$c" }
    fun parseInput(input: List<String>): Pair<String, Rules> {
        return input.first() to input.drop(2)
            .map { it.split("->") }
            .fold(mutableMapOf()) { acc, rule ->
                acc[rule[0].trim()] = rule[1].trim()[0]
                acc
            }
    }

    fun task(input: List<String>, steps: Int): Long {
        val (source, rules) = parseInput(input)

        val charFrequency: Frequencies = source.pairs().fold<String, Frequencies>(mapOf()) { acc, key ->
            acc.merge(calculate(key, rules, steps))
        }.merge(source.charFrequency())

        return charFrequency.values.maxOrNull()!! - charFrequency.values.minOrNull()!!
    }

    fun part1(input: List<String>) = task(input, 10)
    fun part2(input: List<String>) = task(input, 40)

    val input = readInput("day14")
    println(part1(input))
    println(part2(input))
}
