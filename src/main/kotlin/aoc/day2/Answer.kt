package aoc.day2

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
        val positionP1 = plotCourse(inputDataList, false)
        val positionP2 = plotCourse(inputDataList, true)

        println("Part One: $positionP1")
        println("Part Two: $positionP2")
    }

    /**
     * Reads data from a file
     * @return a MutableList with InputData(index, command, value)
     */
    private fun readInputData() : MutableList<InputData> {
        val inputDataList = mutableListOf<InputData>()
        val inputDataPath = Path.of("./data/day2/input")

        Files.readAllLines(inputDataPath).forEachIndexed { index, value ->
            val valueArray = value.split(" ")
            inputDataList.add(InputData(index, valueArray[0], valueArray[1].toInt()))
        }
        return inputDataList
    }

    /**
     * Calculates the final position of the plotted submarine course
     * @return (Horizontal * Depth) as Long
     */
    private fun plotCourse(inputDataList : MutableList<InputData>, isPartTwo : Boolean) : Long {
        val courseData = CourseData(0, 0, 0)
        inputDataList.forEach {
            when (it.command) {
                "forward" -> {
                    courseData.horizontal += it.value
                    if(isPartTwo)
                        courseData.depth += (it.value * courseData.aim)
                }
                "down" -> {
                    if(isPartTwo)
                        courseData.aim += it.value
                    else
                        courseData.depth += it.value
                }
                "up" -> {
                    if(isPartTwo)
                        courseData.aim -= it.value
                    else
                        courseData.depth -= it.value
                }
            }
        }
        return (courseData.horizontal * courseData.depth).toLong()
    }

}