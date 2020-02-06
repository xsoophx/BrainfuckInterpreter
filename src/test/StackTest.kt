import main.BracketHelpStack
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

internal class StackTest {


    companion object {
        private const val command = "--[----->+<]>-----.+++..--.[--->+<]>----.+++[->+++<]>++.++.++++++++.------.[--->+<]>---.-."

        fun `data for adding brackets`() = Stream.of(
            Arguments.of(
                "--[----->+<]>-----.+++..--.[--->+<]>----.+++[->+++<]>++.++.++++++++.------.[--->+<]>---.-.",
                setOf(2, 27, 44, 75)
            ),
            Arguments.of(
                "[][][]",
                setOf(0, 2, 4)
            )
        )

        @JvmStatic
        fun `list without last element`() = Stream.of(
            Arguments.of(
                setOf(1, 7, 3, 9, 4, 3),
                setOf(1, 7, 3, 9, 4)
            )
        )

        @JvmStatic
        fun `last element of list`() = Stream.of(
            Arguments.of(
                setOf(1, 5, 2, 4, 7, 7, 3, 2),
                3
            ),
            Arguments.of(
                setOf(1, 2, 3, 4, 5),
                5

            )
        )

        @JvmStatic
        fun `data mapped while list`() = Stream.of(
            Arguments.of(
                command,
                Pair(11, 2)
            ),
            Arguments.of(
                command,
                Pair(34, 27)
            ),
            Arguments.of(
                command,
                Pair(82, 75)
            )
        )
    }

    @ParameterizedTest
    @MethodSource("data for adding brackets")
    fun `add all Brackets to the stack`(actualInput: String, expectedStackValue: Set<Int>) {
        val newStack = BracketHelpStack(actualInput)
        assertEquals(expectedStackValue, newStack.getHelpStack())
    }

    @ParameterizedTest
    @MethodSource("last element of list")
    fun `remove last element from Stack`(actualInput: Set<Int>, expectedStackValue: Int) {
        val newBracketStack = BracketHelpStack("")
        newBracketStack.stackList = actualInput
        newBracketStack.popStack()
        assertEquals(expectedStackValue, newBracketStack.stackList.last())
    }

    @ParameterizedTest
    @MethodSource("list without last element")
    fun `list after popping`(actualInput: Set<Int>, expectedStackValue: Set<Int>) {
        val newBracketStack = BracketHelpStack("")
        newBracketStack.stackList = actualInput
        newBracketStack.popStack()
        assertEquals(expectedStackValue, newBracketStack.getHelpStack())
    }

    @ParameterizedTest
    @MethodSource("data mapped while list")
    fun `check correct mapping of bracket index to copy`(actualInput: String, expectedStackValue: Pair<Int, Int>) {
        val newBracketStack = BracketHelpStack(actualInput)
        assertEquals(newBracketStack.getHelpMap().elementAt(expectedStackValue.first),expectedStackValue.second)
    }


}