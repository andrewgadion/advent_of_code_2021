import kotlin.math.max
import kotlin.math.min

data class Point(val x: Int, val y: Int)
class LineSegment(private val p1: Point, private val p2: Point) {
    private val isVertical = p1.x == p2.x
    private val isHorizontal = p1.y == p2.y
    val isHorizontalOrVertical get() = isVertical || isHorizontal

    fun iteratePoints(): Sequence<Point> {
        fun smartRange(a: Int, b: Int) = (min(a,b)..max(a,b))
        return sequence {
            when {
                isHorizontal -> smartRange(p1.x, p2.x).forEach { yield(Point(it, p1.y)) }
                isVertical   -> smartRange(p1.y, p2.y).forEach { yield(Point(p1.x, it)) }
                else         -> {
                    val (start, end) = if (p1.x <= p2.x) p1 to p2 else p2 to p1
                    val step = if (start.y <= end.y) 1 else -1
                    (start.x .. end.x).forEachIndexed { index, x -> yield(Point(x, start.y + index * step)) }
                }
            }
        }
    }
}

fun parsePoint(input: String) = input.split(",")
    .map(String::trim).filter(String::isNotEmpty)
    .let { Point(it[0].toInt(), it[1].toInt()) }
fun parseLineSegment(input: String) = input.split("->")
    .map(String::trim).filter(String::isNotEmpty)
    .let { LineSegment(parsePoint(it[0]), parsePoint(it[1]))  }

fun List<LineSegment>.countIntersections(): Int {
    val map = mutableMapOf<Point, Int>()
    this.flatMap(LineSegment::iteratePoints)
        .forEach { p -> map.compute(p) { _, curValue -> (curValue ?: 0) + 1} }
    return map.count { it.value >= 2 }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map(::parseLineSegment)
            .filter(LineSegment::isHorizontalOrVertical)
            .countIntersections()
    }

    fun part2(input: List<String>): Int {
        return input.map(::parseLineSegment).countIntersections()
    }

    val input = readInput("day5")
    println(part1(input))
    println(part2(input))
}
