typealias OctopusMap = List<MutableList<Int>>
typealias Octopus = Triple<Int, Int, Int>

fun OctopusMap.trySet(row: Int, col: Int, compute: (Int) -> Int) {
    if (row in this.indices && col in this[row].indices)
        this[row][col] = compute(this[row][col])
}

fun OctopusMap.flash(row: Int, col: Int): OctopusMap {
    val smartInc = { i: Int -> if (i > 0) i + 1 else i }
    this.trySet(row, col - 1, smartInc)
    this.trySet(row - 1, col - 1, smartInc)
    this.trySet(row - 1, col, smartInc)
    this.trySet(row - 1, col + 1, smartInc)
    this.trySet(row, col + 1, smartInc)
    this.trySet(row + 1, col + 1, smartInc)
    this.trySet(row + 1, col, smartInc)
    this.trySet(row + 1, col - 1, smartInc)
    this[row][col] = 0
    return this
}

fun OctopusMap.iterate(action: (Octopus) -> Unit) {
    this.indices.forEach { row ->
        this[row].indices.forEach { col ->
            action(Triple(row, col, this[row][col]))
        }
    }
}

fun step(map: OctopusMap): Int {
    map.iterate { (row, col, value) -> map[row][col] = value + 1 }
    var totalFlashes = 0
    while (true) {
        var currentFlashes = 0
        map.iterate { (row, col, value) ->
            if (value > 9)
            {
                map.flash(row, col)
                currentFlashes++
            }
        }
        totalFlashes += currentFlashes
        if (currentFlashes == 0)
            break
    }

    return totalFlashes
}

fun main() {
    fun part1(input: List<String>): Int {
        val map = input.map { it.toIntDigits().toMutableList() }
        return (1..100).sumOf { step(map) }
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toIntDigits().toMutableList() }
        return (1..Int.MAX_VALUE).first {
            step(map)
            map.all { row -> row.all { v -> v == 0 } }
        }
    }

    val input = readInput("day11")
    println(part1(input))
    println(part2(input))
}
