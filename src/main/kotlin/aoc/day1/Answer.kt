package aoc.day1

import java.nio.file.Files
import java.nio.file.Path

/**
 * Author: Kai
 */
class Answer {

    fun init() {
        val inputDataList = readInputData()
        val modifiedDataList = readModifiedData(inputDataList)

        println("Part One: ${calculateIncreases(inputDataList)}")
        println("Part Two: ${calculateIncreases(modifiedDataList)}")
    }

    private fun calculateIncreases(dataList : MutableList<InputData>) : Int {
        dataList.forEach {
            val lastIndex = it.index - 1
            if(lastIndex >= 0 && dataList[lastIndex].value < it.value)
                it.increased = true
        }
        return dataList.count { it.increased }
    }

    private fun readInputData() : MutableList<InputData> {
        val inputDataList = mutableListOf<InputData>()
        val inputDataPath = Path.of("./data/day1/input")

        Files.readAllLines(inputDataPath).forEachIndexed { index, value ->
            inputDataList.add(InputData(index, value.toInt(), false))
        }
        return inputDataList
    }

    private fun readModifiedData(inputDataList : MutableList<InputData>) : MutableList<InputData> {
        val modifiedDataList = mutableListOf<InputData>()
        inputDataList.forEach {
            var finalValue = it.value
            for(i in 1..2) {
                if(it.index + i < inputDataList.size)
                    finalValue += inputDataList[it.index + i].value
            }
            modifiedDataList.add(InputData(modifiedDataList.size, finalValue, false))
        }
        return modifiedDataList
    }

}