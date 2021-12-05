import aoc.day3.Answer
import aoc.day3.InputData

val sampleData = listOf("00100", "11110", "10110", "10111", "10101",
    "01111", "00111", "11100", "10000", "11001", "00010", "01010")

fun main() {
    println(Answer().consumption(readInputData()))
    println(Answer().lifeSupport(readInputData()))
}

private fun readInputData() : MutableList<InputData> {
    val inputDataList = mutableListOf<InputData>()
    sampleData.forEachIndexed { index, value ->
        inputDataList.add(InputData(index, value))
    }
    return inputDataList
}