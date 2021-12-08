fun main() {
    fun String.containsAll(chars: String) = chars.all { this.contains(it) }
    fun MutableList<String>.findAndRemove(length: Int, predicate: (String) -> Boolean = { true }): String
    {
        val resultIndex = this.indexOfFirst { it.length == length && predicate(it) }
        return this[resultIndex].also { this.removeAt(resultIndex) }
    }

    fun createMapping(input: String): List<String> {
        val strDigits = input.split(" ").map { it.trim().sort() }.toMutableList()
        val result = MutableList(10) { "" }
        result[1] = strDigits.findAndRemove(2)
        result[4] = strDigits.findAndRemove(4)
        result[7] = strDigits.findAndRemove(3)
        result[8] = strDigits.findAndRemove(7)
        result[9] = strDigits.findAndRemove(6) { it.containsAll(result[4]) }
        result[0] = strDigits.findAndRemove(6) { it.containsAll(result[1]) }
        result[6] = strDigits.findAndRemove(6)
        result[5] = strDigits.findAndRemove(5) { result[6].containsAll(it) }
        result[3] = strDigits.findAndRemove(5) { result[9].containsAll(it) }
        result[2] = strDigits.last()

        return result
    }

    fun decode(input: String, mapping: List<String>): List<Int> {
        return input.split(" ").map(String::trim).map { mapping.indexOf(it.sort()) }
    }

    fun part1(input: List<String>): Int
    {
        val digitsToFind = listOf(1,4,7,8)
        return input.flatMap {
            val (digits, output) = it.split("|").map(String::trim)
            decode(output, createMapping(digits))
        }.count { digitsToFind.contains(it) }
    }

    fun part2(input: List<String>): Int
    {
        return input.sumOf {
            val (digits, output) = it.split("|").map(String::trim)
            decode(output, createMapping(digits)).joinToString("").toInt()
        }
    }

    val input = readInput("day8")
    println(part1(input))
    println(part2(input))
}
