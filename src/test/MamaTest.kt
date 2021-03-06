import main.Interpreter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.AssertionError
import java.util.stream.Stream

internal class MamaTest {


    companion object {

        @JvmStatic
        fun `strings with brackets`() = Stream.of(
            Arguments.of(
                "[This is another Test]", "[]"
            ),
            Arguments.of(
                "++++++++++\n" +
                        " [\n" +
                        "  >+++++++>++++++++++>+++>+<<<<-\n" +
                        " ]                       Schleife zur Vorbereitung der Textausgabe\n" +
                        " >++.                    Ausgabe von 'H'\n" +
                        " >+.                     Ausgabe von 'e'\n" +
                        " +++++++.                'l'\n" +
                        " .                       'l'\n" +
                        " +++.                    'o'\n" +
                        " >++.                    Leerzeichen\n" +
                        " <<+++++++++++++++.      'W'\n" +
                        " >.                      'o'\n" +
                        " +++.                    'r'\n" +
                        " ------.                 'l'\n" +
                        " --------.               'd'\n" +
                        " >+.                     '!'\n" +
                        " >.                      Zeilenvorschub\n" +
                        " +++.                    Wagenrücklauf",
                "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++." +
                        "------.--------.>+.>.+++."
            )
        )

        @JvmStatic
        fun `matching opening and closing chars`() = Stream.of(
            Arguments.of(
                "+++++ +++               Set Cell #0 to 8\n" +
                        " 2 [", 9
            ),
            Arguments.of(
                "[<]", 3

            )
        )

        @JvmStatic
        fun `valid and invalid chars`() = Stream.of(
            Arguments.of(
                '+', true
            ),
            Arguments.of(
                '>', true
            ),
            Arguments.of(
                'f', false
            )
        )

        @JvmStatic
        fun `complementary brackets`() = Stream.of(
            Arguments.of(
                '<', '>'
            ),
            Arguments.of(
                '.', ','
            ),
            Arguments.of(
                '-', '+'
            ),
            Arguments.of(']', '[')
        )

        @JvmStatic
        fun `small commands`() = Stream.of(
            Arguments.of(
                "[+++]", true
            ),
            Arguments.of(
                "[+++", false
            ),
            Arguments.of(
                "", true
            )
        )

        @JvmStatic
        fun `real brainFuck commands`() = Stream.of(
            Arguments.of(
                "--------[-->+++<]>.------------.+.++++++++++.+[---->+<]>+++.-[--->++<]>-.++++++++++.+[---->+<]>+++." +
                        "[->+++<]>+.-[->+++<]>.---[->++++<]>.+++[->+++<]>.[--->+<]>----.+.",
                "this is a test"
            ),
            Arguments.of(
                "++++++++++\n" +
                        " [\n" +
                        "  >+++++++>++++++++++>+++>+<<<<-\n" +
                        " ]                       Schleife zur Vorbereitung der Textausgabe\n" +
                        " >++.                    Ausgabe von 'H'\n" +
                        " >+.                     Ausgabe von 'e'\n" +
                        " +++++++.                'l'\n" +
                        " .                       'l'\n" +
                        " +++.                    'o'\n" +
                        " >++.                    Leerzeichen\n" +
                        " <<+++++++++++++++.      'W'\n" +
                        " >.                      'o'\n" +
                        " +++.                    'r'\n" +
                        " ------.                 'l'\n" +
                        " --------.               'd'\n" +
                        " >+.                     '!'\n" +
                        " >.                      Zeilenvorschub\n" +
                        " +++.                    Wagenrücklauf",
                "Hello World!\n\r"
            )
        )
    }

    @ParameterizedTest
    @MethodSource("valid and invalid chars")
    fun `Does enum class contain character`(input: Char, expected: Boolean) {
        val interpreter = Interpreter()
        val enumContainsChar = interpreter.enumClassContains(input)
        assertEquals(expected, enumContainsChar)
    }


    @ParameterizedTest
    @MethodSource("strings with brackets")
    fun `Everything between actual BrainFuck chars should be removed`(input: String, expected: String) {
        val interpreter = Interpreter()
        val cleanedUpString = interpreter.cleanedUpVariable(input)
        assertEquals(expected, cleanedUpString)
    }

    @ParameterizedTest
    @MethodSource("small commands")
    fun `Check if command is valid`(input: String, shouldBeValid: Boolean) {
        val interpreter = Interpreter()
        if (shouldBeValid) {
            assertDoesNotThrow {
                interpreter.translate(input)
            }
        } else {
            assertThrows<AssertionError> {
                interpreter.translate(input)
            }
        }
    }

    @ParameterizedTest
    @MethodSource("real brainFuck commands")
    fun `Check if script is interpreted right`(input: String, validOutput: String) {
        val interpreter = Interpreter()
        assertEquals(validOutput, interpreter.translate(input))

    }
}