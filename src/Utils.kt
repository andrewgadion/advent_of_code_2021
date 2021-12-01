import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/input", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
//fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)