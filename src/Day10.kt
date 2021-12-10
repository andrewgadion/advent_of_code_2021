import java.util.*

val opened = listOf('(', '[', '{', '<')
val closed = listOf(')', ']', '}', '>')
fun Char.getClosing() = closed[opened.indexOf(this)]

fun findIncorrectChar(line: String): Char? {
    val stack = Stack<Char>()
    line.forEach {
        if (stack.empty() || it in opened)
            stack.add(it)
        else if (stack.pop().getClosing() != it)
            return it
    }
    return null
}
fun getPointsForIncorrect(c: Char) = when(c) {
    ')' -> 3
    ']' -> 57
    '}' -> 1197
    '>' -> 25137
    else -> throw Exception("No points for char")
}

fun completeLineScore(line: String): Long {
    val stack = Stack<Char>()
    line.forEach {
        if (stack.empty() || it in opened)
            stack.add(it)
        else stack.pop()
    }
    var score = 0L
    while (stack.isNotEmpty())
        score = score * 5 + (closed.indexOf(stack.pop().getClosing()) + 1)

    return score
}

fun main() {

    fun part1(input: List<String>): Int = input.mapNotNull(::findIncorrectChar).sumOf(::getPointsForIncorrect)

    fun part2(input: List<String>): Long = input
        .filter { findIncorrectChar(it) == null }
        .map(::completeLineScore)
        .sorted().let { it[it.size / 2] }

    val input = readInput("day10")
    println(part1(input))
    println(part2(input))
}
