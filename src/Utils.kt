import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/input", "$name.txt").readLines()

fun String.toIntList(delimiter: String = ","): List<Int> =
    this.split(delimiter)
        .map(String::trim)
        .filter(String::isNotEmpty)
        .map(String::toInt)

fun String.sort(): String {
    return this.toCharArray().sorted().joinToString("")
}

fun String.toIntDigits() = this.map(Char::digitToInt)