typealias HeightMap = List<List<Int>>
typealias HeightPoint = Triple<Int, Int, Int>

fun HeightMap.tryGet(row: Int, col: Int): HeightPoint? =
    if (row in this.indices && col in this[row].indices) HeightPoint(row, col, this[row][col]) else null

fun HeightMap.allNeighbours(row: Int, col: Int) = listOfNotNull(
    tryGet(row - 1, col),
    tryGet(row, col - 1),
    tryGet(row + 1, col),
    tryGet(row, col + 1))

fun HeightMap.lowPoints(): List<HeightPoint> {
    val lowPoints = mutableListOf<HeightPoint>()
    this.forEachIndexed { row, digits ->
        digits.forEachIndexed { column, height ->
            if (this.allNeighbours(row, column).all { it.third > height })
                lowPoints.add(HeightPoint(row, column, height))
        }
    }
    return lowPoints
}

fun HeightMap.findBasin(point: HeightPoint): List<HeightPoint> {
    fun HeightPoint.addOwnBasin(destBasin: MutableList<HeightPoint>): List<HeightPoint> {
        val (row, col, value) = this
        val heightRange = (value + 1)..8
        destBasin.add(this)
        allNeighbours(row, col).filter { it.third in heightRange }.forEach { it.addOwnBasin(destBasin) }
        return destBasin
    }
    return point.addOwnBasin(mutableListOf()).distinct()
}

fun main() {
    fun part1(input: List<String>): Int
    {
        val heightMap = input.map(String::toIntDigits)
        return heightMap.lowPoints().sumOf { heightMap[it.first][it.second] + 1 }
    }

    fun part2(input: List<String>): Int
    {
        val heightMap = input.map(String::toIntDigits)
        val basins = heightMap
            .lowPoints()
            .map { heightMap.findBasin(it) }
        return basins.map { it.size }
            .sortedDescending()
            .take(3)
            .fold(1) { acc, i -> i * acc }
    }

    val input = readInput("day9")
    println(part1(input))
    println(part2(input))
}
