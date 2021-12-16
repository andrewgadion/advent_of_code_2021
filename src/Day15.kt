import java.util.PriorityQueue

fun main() {

    data class Node(val X: Int, val Y: Int, val Value: Int)
    class Path(val LastNode: Node, val Score: Int)
    fun Path.withNewNode(n: Node): Path = Path(n, Score + n.Value)

    fun Array<Array<Node>>.connections(n: Node) = listOfNotNull(
        getOrNull(n.Y - 1)?.getOrNull(n.X),
        getOrNull(n.Y + 1)?.getOrNull(n.X),
        getOrNull(n.Y)?.getOrNull(n.X - 1),
        getOrNull(n.Y)?.getOrNull(n.X + 1))

    fun findPath(nodesMap: Array<Array<Node>>, start: Node, last: Node): Path? {
        val passed = mutableSetOf<Node>()
        val availablePaths = PriorityQueue<Path> { p1 , p2 -> p1.Score.compareTo(p2.Score) }
        availablePaths.add(Path(start, 0))
        while (availablePaths.isNotEmpty()) {
            val curPath = availablePaths.poll()
            val lastNode = curPath.LastNode
            if (lastNode == last)
                return curPath

            if (passed.contains(lastNode))
                continue
            passed.add(lastNode)
            nodesMap.connections(lastNode).forEach {
                availablePaths.add(curPath.withNewNode(it))
            }
        }
        return null
    }

    fun parseInput(input: List<String>, multiplier: Int = 1): Array<Array<Node>>
    {
        val digits = input.map { it.toIntDigits() }
        return Array(input.size * multiplier) { y ->
            Array(input.size * multiplier) { x ->
                val (xSh, ySh) = x / input.size to y / input.size
                val (baseX, baseY) = x % input.size to y % input.size
                val value = (digits[baseX][baseY] + xSh + ySh) % 9
                Node(x, y, if (value == 0) 9 else value)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val nodes = parseInput(input)
        return findPath(nodes, nodes.first().first(), nodes.last().last())?.Score ?: 0
    }

    fun part2(input: List<String>): Int {
        val nodes = parseInput(input, 5)
        return findPath(nodes, nodes.first().first(), nodes.last().last())?.Score ?: 0
    }

    val input = readInput("day15")
    println(part1(input))
    println(part2(input))
}
