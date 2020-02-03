import main.Stack
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class StackTest{

    @ParameterizedTest
    @Disabled
    @MethodSource("initial input to validate stack")
    fun `split command into Sets of commands`(actualInput: String, expectedStackValue: Boolean){
        val newStack = Stack(actualInput.toList())
    }
}