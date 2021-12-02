fun main() {

    fun part1(input: List<String>): Int {
        return input.map {
            val value = it.split(" ")
            value[0] to value[1].toInt()
        }.fold(0 to 0) { (horizontal, depth), (direction, value) ->
            when(direction) {
                "forward" -> horizontal+ value to depth
                "down" -> horizontal to depth + value
                "up" -> horizontal to depth - value
                else -> throw Exception()
            }
        }.let { it.first * it.second}
    }

    fun part2(input: List<String>): Int {
        return input.map {
            val value = it.split(" ")
            value[0] to value[1].toInt()
        }.fold(Triple(0, 0, 0)) { (horizontal, depth, aim), (direction, value) ->
            when(direction) {
                "forward" -> Triple(horizontal + value, depth + aim * value, aim)
                "down" -> Triple(horizontal, depth, aim + value)
                "up" -> Triple(horizontal, depth, aim - value)
                else -> throw Exception()
            }
        }.let { it.first * it.second}
    }

    val input = readInput("day2")
    println(part1(input))
    println(part2(input))
}
