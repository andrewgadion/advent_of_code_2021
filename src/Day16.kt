abstract class Packet(val Version: Int, val Id: Int) {
    abstract val Value: Long
}
class LiteralPacket(version: Int, id: Int, override val Value: Long) : Packet(version, id)
class OperatorPacket(version: Int, id: Int, val Packets: List<Packet>) : Packet(version, id) {
    override val Value: Long
        get() = when (Id) {
            0 -> Packets.sumOf { it.Value }
            1 -> Packets.fold(1L) { acc, p -> acc * p.Value}
            2 -> Packets.minOf { it.Value }
            3 -> Packets.maxOf { it.Value }
            5 -> if (Packets[0].Value > Packets[1].Value) 1 else 0
            6 -> if (Packets[1].Value > Packets[0].Value) 1 else 0
            7 -> if (Packets[0].Value == Packets[1].Value) 1 else 0
            else -> throw Exception("Illegal Id")
        }
}

fun <T> Packet.all(action: (Packet) -> T): List<T> {
    return when (this) {
        is OperatorPacket -> listOf(action(this)) + this.Packets.flatMap { it.all(action) }
        else -> listOf(action(this))
    }
}

class StringParser(private val Input: String) {
    private var Position = 0
        private set

    private val AnyLeft get() = Input.length - Position > 6

    private fun <T> parse(parseFunction: (String) -> Pair<T, Int>): T {
        return parseFunction(Input.substring(Position)).let {
            Position += it.second
            it.first
        }
    }

    private fun window(size: Int): StringParser {
        return StringParser(Input.substring(Position, Position + size)).also {
            Position += size
        }
    }

    private fun parse3Bits(input: String) = input.substring(0, 3).toInt(2) to 3
    private fun parseLiteral(input: String): Pair<Long, Int> {
        var result = ""
        var position = 0
        while (position < input.length) {
            val instruction = input.substring(position, position + 5)
            result += instruction.substring(1)
            position += 5
            if (instruction[0] == '0')
                break
        }
        return result.toLong(2) to position
    }

    fun parsePacket(): Packet {
        val version = parse(::parse3Bits)
        return when (val id = parse(::parse3Bits)) {
            4 -> LiteralPacket(version, id, parse(::parseLiteral))
            else -> {
                val lengthTypeId = parse { (it[0] == '0') to 1 }
                if (lengthTypeId) {
                    val size = parse { it.substring(0, 15).toInt(2) to 15 }
                    OperatorPacket(version, id, window(size).parsePackets())
                } else {
                    val packetsCount = parse { it.substring(0, 11).toInt(2) to 11 }
                    OperatorPacket(version, id, (0 until packetsCount).map { parsePacket() })
                }
            }
        }
    }

    private fun parsePackets(): List<Packet> {
        val packets = mutableListOf<Packet>()
        while (AnyLeft)
            packets.add(parsePacket())

        return packets
    }
}


fun main() {
    fun parseInput(input: List<String>): Packet {
        val parser = StringParser(input.flatMap { it.windowed(1, 1) }.joinToString("") {
            it.toInt(16).toString(2).padStart(4, '0')
        })
        return parser.parsePacket()
    }

    fun part1(input: List<String>) = parseInput(input).all { it.Version }.sum()

    fun part2(input: List<String>) = parseInput(input).Value

    val input = readInput("day16")
    println(part1(input))
    println(part2(input))
}
