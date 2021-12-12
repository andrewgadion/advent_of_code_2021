typealias Connections = Map<String, List<String>>
typealias Path = List<String>

fun main() {

    fun Connections.countAllPaths(currentPath: Path, canVisit: (Path, String) -> Boolean): Int {
        val lastPoint = currentPath.last()
        if (lastPoint == "end")
            return 1

        return this[lastPoint]!!.filter { canVisit(currentPath, it) }
            .sumOf { this.countAllPaths(currentPath.plus(it), canVisit) }
    }

    fun parseInput(input: List<String>): Connections {
        val connections = mutableMapOf<String, List<String>>()
        fun connect(a: String, b: String) = connections.compute(a) { _, prev -> prev?.plus(b) ?: listOf(b) }
        input.forEach {
            val (from, to) = it.split("-")
            connect(from, to)
            connect(to, from)
        }
        return connections
    }

    fun part1(input: List<String>): Int {
        return parseInput(input).countAllPaths(listOf("start")) { curPath, newPoint ->
            newPoint.isUpperCase() || !curPath.contains(newPoint)
        }
    }

    fun part2(input: List<String>): Int {
        return parseInput(input).countAllPaths(listOf("start")) { curPath, newPoint ->
            when {
                newPoint.isUpperCase() -> true
                newPoint == "start" -> false
                curPath.filter { it.isLowerCase() }.hasEqualEntries() -> !curPath.contains(newPoint)
                else -> true
            }
        }
    }

    val input = readInput("day12")
    println(part1(input))
    println(part2(input))
}
