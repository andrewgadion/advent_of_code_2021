
class Board(data: List<String>) {
    private val boardSize: Int = data.size
    private val cells = data
        .mapIndexed { col, str ->
            str.split(" ")
                .filter { it.isNotEmpty() }
                .mapIndexed { row, numStr -> Cell(col, row, numStr.toInt()) }
        }
        .flatten()

    class Cell(val col: Int, val row: Int, val number: Int) {
        var isMarked: Boolean = false

        fun mark(value: Int): Boolean {
            if (isMarked) return true
            if (number == value)
                isMarked = true
            return isMarked
        }

        override fun toString() = "$number $isMarked"
    }

    fun sumOfAllUnmarked()
        = cells.filterNot { it.isMarked }.sumOf { it.number }

    fun mark(number: Int): Boolean {
        val markedColumn = MutableList(boardSize) { 0 }
        val markedRow = MutableList(boardSize) { 0 }

        cells.forEach { cell ->
            if (cell.mark(number)) {
                markedColumn[cell.col]++
                markedRow[cell.row]++

                if (markedColumn[cell.col] == boardSize || markedRow[cell.row] == boardSize)
                    return true
            }
        }

        return false
    }
}

fun main() {

    fun parseData(input: List<String>): Pair<List<Int>, List<Board>> {
        val numbers = input[0].split(",").map { it.toInt() }
        val boards = input.drop(1).filter { it.isNotEmpty() }.chunked(5, ::Board)
        return numbers to boards
    }

    fun part1(input: List<String>): Int {
        val (numbers, boards) = parseData(input)
        numbers.forEach { number ->
            val winningBoard = boards.find { it.mark(number) }
            if (winningBoard != null)
                return winningBoard.sumOfAllUnmarked() * number
        }

        return -1
    }

    fun part2(input: List<String>): Int {
        val data = parseData(input)
        val numbers = data.first
        var boards = data.second
        numbers.forEach { number ->
            val boardLastToWin = boards.first()
            boards = boards.filterNot { it.mark(number) }
            if (boards.isEmpty())
                return boardLastToWin.sumOfAllUnmarked() * number
        }
        return -1
    }

    val input = readInput("day4")
    println(part1(input))
    println(part2(input))
}
