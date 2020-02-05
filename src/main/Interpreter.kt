package main

import java.lang.IllegalArgumentException


fun main() {
    val currentBaseClass = Interpreter()
    currentBaseClass.printFinalOutput()
}

class Interpreter {

    private lateinit var localCleanedUpCommand: String
    private var counterOpeningBrackets = 0
    private var counterClosingBrackets = 0
    private var overallBracketCounter = 0

    var isStackValid: Boolean = similarBrackets()

    fun similarBrackets(): Boolean = (counterClosingBrackets == counterOpeningBrackets) && (overallBracketCounter < 0)


    companion object {
        val outputOfCurrentText = "Here will be test Objects for interpreting"

        val inputCommand = "[ This program prints \"Hello World!\" and a newline to the screen, its\n" +
                "  length is 106 active command characters. [It is not the shortest.]\n" +
                "\n" +
                "  This loop is an \"initial comment loop\", a simple way of adding a comment\n" +
                "  to a BF program such that you don't have to worry about any command\n" +
                "  characters. Any \".\", \",\", \"+\", \"-\", \"<\" and \">\" characters are simply\n" +
                "  ignored, the \"[\" and \"]\" characters just have to be balanced. This\n" +
                "  loop and the commands it contains are ignored because the current cell\n" +
                "  defaults to a value of 0; the 0 value causes this loop to be skipped.\n" +
                "]\n" +
                "++++++++               Set Cell #0 to 8\n" +
                "["
    }

    private enum class ValidCharacters(val pairOfCommands: Pair<Char, Char>) {
        PointerIncrementDecrement(Pair('>', '<')),
        PointerValueIncrementDecrement(Pair('+', '-')),
        PutGetChar(Pair('.', ',')),
        WhileLoop(Pair('[', ']'))
    }

    fun printFinalOutput() {
        println(outputOfCurrentText)
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

    fun getComplementaryPairValue(currentChar: Char): Char {
        ValidCharacters.values().forEach {
            if (it.pairOfCommands.first == currentChar)
                return it.pairOfCommands.second
            else if (it.pairOfCommands.second == currentChar)
                return it.pairOfCommands.first
        }
        return currentChar
    }


    fun addNewCommand(command: String) {
        localCleanedUpCommand = cleanedUpVariable(command)
        if (!isStackValid)
            println("Your command isn't valid")
        return
    }

    private fun checkValidCommandCharInput(currentCharacter: Char) {
        when (currentCharacter) {
            '[' -> {
                ++overallBracketCounter; ++counterOpeningBrackets
            }
            ']' -> {
                --overallBracketCounter; ++counterClosingBrackets
            }
            else -> return
        }
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