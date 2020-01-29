package main


fun main(){
    val currentBaseClass = MainInterpreterBase()
    currentBaseClass.printfinalOutput()
}



class MainInterpreterBase{

    init {
        var BracketStack = setOf<Char>()
    }

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

    private enum class ValidCharacters (val PairOfCommands: Pair<Char, Char>){
        PointerIncrementDecrement(Pair('>', '<')),
        PointerValueIncrementDecrement (Pair('+', '-')),
        PutGetChar (Pair('.', ',')),
        WhileLoop(Pair('[', ']')),
    }

    fun printfinalOutput(){
        println(outputOfCurrentText)
    }

    fun enumClassContains(currentCharacter: Char): Boolean {
        return enumValues<ValidCharacters>().any {
            it.PairOfCommands.first == currentCharacter || it.PairOfCommands.second == currentCharacter
        }
    }

    fun cleanedUpVariable (input: String): String {
        var cleanedUpCommand = ""
        input.forEach { char ->
            if (enumClassContains(char))
                cleanedUpCommand += char
        }
        return cleanedUpCommand
    }

    fun getComplementaryPairValue (givenValue: Char): Char {
            ValidCharacters.values().find {
                  (it.PairOfCommands.first == givenValue)
                    return complementOfPair(it.PairOfCommands, givenValue)
            }
            ValidCharacters.values().find {
                (it.PairOfCommands.second == givenValue)
                return complementOfPair(it.PairOfCommands, givenValue)
            }

    }

    fun complementOfPair(currentPair: Pair<Char, Char>, givenChar: Char): Char{
        return if (currentPair.first == givenChar) currentPair.second else currentPair.first
    }

}
