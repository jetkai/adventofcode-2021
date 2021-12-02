package aoc.day1

import kotlinx.serialization.Serializable

@Serializable
class InputData(val index : Int, val value : Int, var increased : Boolean)