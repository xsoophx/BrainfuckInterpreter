import main.MainInterpreterBase
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertNull
import java.util.stream.Stream

internal class MamaTest {

    companion object{
        @JvmStatic
        fun `Data for testing interpreter`() = Stream.of(
            Arguments.of(
                "Here will be test Objects for interpreting"),
            Arguments.of(
                "And here will be another String for testing"
            )
        )
        }

    @Test
    fun `initialize Base class`() {
        val newBaseClass = MainInterpreterBase()
        assertNull(newBaseClass)
    }

    @ParameterizedTest
    @MethodSource("Data for testing interpreter")
    fun`Test correct output after interpreting`(actualOutput: String){
        assertEquals("", "actualOutput")
    }
}