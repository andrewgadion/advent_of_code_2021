fun main() {
    tailrec fun runGenerations(population: List<Long>, daysLeft: Int): List<Long> {
        if (daysLeft == 0) return population
        val nextGenPopulation = population.indices.map { i ->
            if (i == 6) population[7] + population[0] else population[(i + 1) % population.size]
        }
        return runGenerations(nextGenPopulation, daysLeft - 1)
    }

    fun parsePopulation(input: List<String>): List<Long> {
        return input
            .flatMap { it.split(",") }.map(String::toInt)
            .fold(MutableList(9) { 0 }) { acc, cur -> acc[cur]++; acc }
    }

    fun part1(input: List<String>)
        = runGenerations(parsePopulation(input), 80).sum()

    fun part2(input: List<String>)
        = runGenerations(parsePopulation(input), 256).sum()

    val input = readInput("day6")
    println(part1(input))
    println(part2(input))
}
