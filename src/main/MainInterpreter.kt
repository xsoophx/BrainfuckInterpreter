package main

import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream


fun main(){
    val currentBaseClass = MainInterpreterBase()
    currentBaseClass.printFinalOutput()
}

class MainInterpreterBase{

    private var bracketStack = Stack<Char>()
    private lateinit var localCleanedUpCommand: String
    private var commandStack = setOf<Stack<Char>>()
    private var counterOpeningBrackets = 0
    private var counterClosingBrackets = 0

    val isStackValid: Boolean
        get() = counterClosingBrackets == counterOpeningBrackets


    companion object{
        @JvmStatic
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

    private enum class ValidCharacters (val pairOfCommands: Pair<Char, Char>){
        PointerIncrementDecrement(Pair('>', '<')),
        PointerValueIncrementDecrement (Pair('+', '-')),
        PutGetChar (Pair('.', ',')),
        WhileLoop(Pair('[', ']'))
    }

    fun printFinalOutput(){
        println(outputOfCurrentText)
    }

    fun enumClassContains(currentCharacter: Char): Boolean {
        return enumValues<ValidCharacters>().any {
            it.pairOfCommands.first == currentCharacter || it.pairOfCommands.second == currentCharacter
        }
    }

    fun cleanedUpVariable (input: String): String {
        var cleanedUpCommand = ""
        input.forEach { char ->
            if (enumClassContains(char)) {
                cleanedUpCommand += char
                checkValidCommandCharInput(char)
            }
        }
        localCleanedUpCommand = cleanedUpCommand
        return cleanedUpCommand
    }

    fun getComplementaryPairValue (currentChar: Char): Char{
        ValidCharacters.values().forEach {
            if (it.pairOfCommands.first == currentChar)
                return it.pairOfCommands.second
            else if (it.pairOfCommands.second == currentChar)
                return it.pairOfCommands.first
        }
        return currentChar
    }

    fun addCommandToLocalStack(){
        bracketStack.add(localCleanedUpCommand.toList())
    }

    fun addNewCommand(command: String){
        localCleanedUpCommand = cleanedUpVariable(command)
        if (!isStackValid)
            println("Your command isn't valid")
        return
    }


    private fun splitCommandsToSets(){
        if (isStackValid) {
            localCleanedUpCommand.split('[', ']', ignoreCase = true).forEach { command ->
                commandStack += Stack(command.toList())
            }
        }
        else
            println("Your command is not valid")
    }

    private fun checkValidCommandCharInput(currentCharacter: Char) {
        when (currentCharacter) {
            '[' -> ++counterOpeningBrackets
            ']' -> ++counterClosingBrackets
            else -> return
        }
    }

}