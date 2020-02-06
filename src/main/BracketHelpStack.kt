package main

class BracketHelpStack(command: String) {
    var stackList = setOf<Int>()
    private var loopList = IntArray(command.length)

    init {
        buildHelpStack(command)
    }

    fun popStack(): Int {
        val tempIndex = stackList.last()
        stackList.drop(tempIndex)
        return tempIndex
    }

    private fun pushStack(newElement: Int) {
        stackList += newElement
    }

    fun getHelpStack(): Set<Int> {
        return stackList
    }

    fun getHelpMap(): IntArray {
        return loopList
    }

    private fun buildHelpStack(command: String) {
        command.forEachIndexed { index, element ->
            if (element == '[')
                pushStack(index)
            if (element == ']') {
                val startingPosition = popStack()
                loopList[index] = startingPosition
            }
        }
    }

}

