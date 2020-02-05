package main

import java.lang.IllegalArgumentException


fun main() {
    val currentBaseClass = Interpreter()
}

class Interpreter {

    private lateinit var localCleanedUpCommand: String
    private var counterOpeningBrackets = 0
    private var counterClosingBrackets = 0
    private var overallBracketCounter = 0

    var isStackValid: Boolean = similarBrackets()

    fun similarBrackets(): Boolean = (counterClosingBrackets == counterOpeningBrackets) && (overallBracketCounter < 0)

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

    fun addNewCommand(command: String) {
        localCleanedUpCommand = cleanedUpVariable(command)
        if (!isStackValid)
            println("Your command isn't valid")
        return
    }


    fun interpretCommand(): String{
        val loopMap = BracketHelpStack(localCleanedUpCommand).getHelpMap()
        var codePosition = 0
        var arrayPointer = 0
        val valueArray = MutableList<Byte>(1000) { it -> 0}
        var returnString = ""

        while (codePosition < localCleanedUpCommand.length) {
            when(localCleanedUpCommand[codePosition]){
                '>' -> ++arrayPointer
                '<' -> --arrayPointer
                '+' -> valueArray[arrayPointer] = (valueArray[arrayPointer] + 1).toByte()
                '-' -> valueArray[arrayPointer] = (valueArray[arrayPointer] - 1).toByte()
                '.' -> returnString += (valueArray[arrayPointer]).toChar()
                ',' -> valueArray[arrayPointer] = (localCleanedUpCommand[codePosition]).toByte()
                ']' -> if (valueArray[arrayPointer].toInt() != 0) codePosition = loopMap[codePosition]
                '[' -> if (valueArray[arrayPointer].toInt() == 0)
                else -> throw IllegalArgumentException()
            }
            codePosition++
        }
        return returnString
    }
}