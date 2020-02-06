package main

import java.lang.IllegalArgumentException
import kotlin.test.assertNotNull

class Interpreter {

    private enum class ValidCharacters(val pairOfCommands: Pair<Char, Char>) {
        PointerIncrementDecrement(Pair('>', '<')),
        PointerValueIncrementDecrement(Pair('+', '-')),
        PutGetChar(Pair('.', ',')),
        WhileLoop(Pair('[', ']'))
    }

    fun enumClassContains(currentCharacter: Char): Boolean {
        return enumValues<ValidCharacters>().any {
            it.pairOfCommands.first == currentCharacter || it.pairOfCommands.second == currentCharacter
        }
    }

    private data class CleanState(
        val cleaned: String = "",
        val brackets: Int = 0
    )

    fun cleanedUpVariable(input: String): String {
        return input.asSequence().filter {
            enumClassContains(it)
        }.fold(CleanState()) { state, char ->
            assert(state.brackets >= 0)
            CleanState(
                cleaned = state.cleaned + char,
                brackets = when (char) {
                    '[' -> state.brackets + 1
                    ']' -> state.brackets - 1
                    else -> state.brackets
                }
            )
        }.also { (_, brackets) ->
            assert(brackets == 0)
        }.cleaned

    }

    fun translate(command: String?):String {
        assertNotNull(command)
        return interpretCommand(cleanedUpVariable(command))

    }

    private fun interpretCommand(command: String): String{
        val loopMap = BracketHelpStack(command).getHelpMap()
        var codePosition = 0
        var arrayPointer = 0
        val valueArray = MutableList<Byte>(1000) {0}
        var returnString = ""

        while (codePosition < command.length) {
            when(command[codePosition]){
                '>' -> ++arrayPointer
                '<' -> --arrayPointer
                '+' -> valueArray[arrayPointer] = (valueArray[arrayPointer] + 1).toByte()
                '-' -> valueArray[arrayPointer] = (valueArray[arrayPointer] - 1).toByte()
                '.' -> returnString += (valueArray[arrayPointer]).toChar()
                ',' -> valueArray[arrayPointer] = (command[codePosition]).toByte()
                ']' -> if (valueArray[arrayPointer].toInt() != 0) codePosition = loopMap[codePosition]
                '[' -> {}
                else -> throw IllegalArgumentException()
            }
            codePosition++
        }
        return returnString
    }
}