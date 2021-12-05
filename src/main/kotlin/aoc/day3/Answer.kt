package aoc.day3

import java.nio.file.Files
import java.nio.file.Path

/**
 * Author: Kai
 */
class Answer {

    /**
     * Initializes the core functions within this class
     * Prints the answers to the console
     */
    fun init() {
        val inputDataList = readInputData()

        println("Part One ${consumption(inputDataList)}")
        println("Part Two: ${lifeSupport(inputDataList)}")
    }

    /**
     * Reads data from a file
     * @return a MutableList with InputData(index, binary)
     */
    private fun readInputData() : MutableList<InputData> {
        val inputDataList = mutableListOf<InputData>()
        val inputDataPath = Path.of("./data/day3/input")

        Files.readAllLines(inputDataPath).forEachIndexed { index, value ->
            inputDataList.add(InputData(index, value))
        }
        return inputDataList
    }

    /**
     * Finds the Power Consumption Usage
     * @return (gamma * epsilon) as Int
     */
     fun consumption(inputDataList: MutableList<InputData>): Int {
        val binaryLength = inputDataList[0].binary.length //12
        val binaryRateData = BinaryRateData("", "", "", "")
        for (i in 0 until binaryLength)
            slowChecker1(i, inputDataList, binaryRateData)
        val gamma = binaryRateData.gamma.toInt(2)
        val epsilon = binaryRateData.epsilon.toInt(2)
        return gamma * epsilon
    }

    /**
     * Finds the Life Support Rating
     * @return (oxygenRating * co2Rating) as Int
     */
    fun lifeSupport(inputDataList: MutableList<InputData>): Int {
        val binaryLength = inputDataList[0].binary.length //12
        val binaryRateData = BinaryRateData("", "", "", "")
        val binaryLists = mutableListOf(inputDataList.toMutableList(), inputDataList.toMutableList())

        for (i in 0 until binaryLength) {
            for(binaryList in binaryLists) {
                val isOxygen = binaryLists[0] == binaryList
                slowChecker2(i, binaryList, binaryRateData, isOxygen)

                val oIterator = binaryList.iterator()
                while(oIterator.hasNext()) {
                    val next = oIterator.next()
                    val nextBinaryRateData : Char = if(isOxygen)
                        binaryRateData.oxygen[i]
                    else
                        binaryRateData.co2[i]
                    if(next.binary[i] != nextBinaryRateData && binaryList.size > 1)
                        oIterator.remove()
                }
            }
        }

        binaryRateData.oxygen = binaryLists[0][0].binary
        binaryRateData.co2 = binaryLists[1][0].binary

        val oxygenRating = binaryRateData.oxygen.toInt(2)
        val co2Rating = binaryRateData.co2.toInt(2)

        /** Correct
         * 010101101111
         * 100110010111
         * Oxy: 1391, Co2: 2455
         * Part Two: 3414905
         */
        return oxygenRating * co2Rating
    }

    /**
     * Loops through each Bit within a Binary String, calculating the Most & Least Common
     * Manually comparing if entries are equal, if so use the highest (Otherwise first index will be chosen by default)
     * @return BinaryRateData -> Gamma & Epsilon
     */
    private fun slowChecker1(index : Int, inputDataList : MutableList<InputData>, binaryRateData : BinaryRateData) : BinaryRateData {
        var builder = ""
        inputDataList.forEach { builder += it.binary[index] }
        binaryRateData.gamma += mostCommon(builder)
        binaryRateData.epsilon += leastCommon(builder)
        return binaryRateData
    }

    /**
     * Loops through each Bit within a Binary String, calculating the Most & Least Common
     * Manually comparing if entries are equal, if so use the highest (Otherwise first index will be chosen by default)
     * @return BinaryRateData -> Oxygen & CO2
     */
    private fun slowChecker2(index : Int, inputDataList : MutableList<InputData>, binaryRateData : BinaryRateData, isOxygen : Boolean) : BinaryRateData {
        var builder = ""
        inputDataList.forEach { builder += it.binary[index] }
        if(isOxygen)
            binaryRateData.oxygen += mostCommon(builder)
        else
            binaryRateData.co2 += leastCommon(builder)
        return binaryRateData
    }

    /**
     * Calculates the Most Common Value
     * Manually comparing if entries are equal:
     *  - If so, use the highest (Otherwise first index will be chosen by default)
     *  - If not, use the most common value
     */
    private fun mostCommon(input : String): String {
        val entries = input.groupingBy { it }.eachCount().values
        return if(entries.size > 1 && entries.elementAt(0) == entries.elementAt(1))
            "1"
        else
            input.groupingBy { it }.eachCount().maxByOrNull{ it.value }?.key.toString()
    }

    /**
     * Calculates the Least Common Value
     * Manually comparing if entries are equal, if so use the highest (Otherwise first index will be chosen by default)
     *  - If so, use the lowest (Otherwise first index will be chosen by default)
     *  - If not, use the least common value
     */
    private fun leastCommon(input : String): String {
        val entries = input.groupingBy { it }.eachCount().values
        return if(entries.size > 1 && entries.elementAt(0) == entries.elementAt(1))
            "0"
        else
            input.groupingBy { it }.eachCount().minByOrNull{ it.value }?.key.toString()
    }

}