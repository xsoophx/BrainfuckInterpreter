import main.MainInterpreterBase
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class MamaTest {
    private lateinit var newBaseClass: MainInterpreterBase

    @BeforeEach
    fun SetUp(){
        newBaseClass = MainInterpreterBase()
    }

    companion object {
        @JvmStatic
        fun `Data for testing interpreter`() = Stream.of(
            Arguments.of(
                "Here will be test Objects for interpreting",
                MainInterpreterBase.outputOfCurrentText
                )
        )

        @JvmStatic
        fun `Strings with Brackets` () = Stream.of(
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
        fun `Matching opening and closing chars`()= Stream.of(
            Arguments.of(
                "+++++ +++               Set Cell #0 to 8\n" +
                        " 2 [" , 9
            ),
            Arguments.of(
                "[<]", 3

        )
        )
        @JvmStatic
        fun `Valid and invalid Chars`()= Stream.of(
            Arguments.of(
                '+' , true),
            Arguments.of(
                '>', true),
            Arguments.of(
                'f', false
        )
        )
        }

    @ParameterizedTest
    @MethodSource("Matching opening and closing chars")
    fun `Count opening and closing symbols`(actualInput: String, expected: Int){
        var numberOfCharsInInput = newBaseClass.cleanedUpVariable(actualInput)
        assertEquals(expected, numberOfCharsInInput.count())
    }

    @ParameterizedTest
    @MethodSource("Valid and invalid Chars")
    fun `Does enum class contain character`(actualInput: Char, expected: Boolean){
        var enumContainsChar = newBaseClass.enumClassContains(actualInput)
        assertEquals(expected, enumContainsChar)
    }

    @Disabled
    @MethodSource("Data for testing interpreter")
    fun`Test correct output after interpreting`(expected: String, actualOutput: String){
       assertEquals( "Here will be test Objects for interpreting", actualOutput)
    }

    @ParameterizedTest
    @MethodSource("Strings with Brackets")
    fun `Everything between actual BF chars should be removed`(actualInput: String, expected: String){
        var cleanedUpString = newBaseClass.cleanedUpVariable(actualInput)
        assertEquals(expected, cleanedUpString)
    }
}