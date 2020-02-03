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
    private lateinit var localStackTest: StackTest

    @BeforeEach
    fun SetUp(){
        newBaseClass = MainInterpreterBase()
        localStackTest = StackTest()
    }

    companion object {
        @JvmStatic
        fun `data for testing interpreter`() = Stream.of(
            Arguments.of(
                "Here will be test Objects for interpreting",
                MainInterpreterBase.outputOfCurrentText
                )
        )

        @JvmStatic
        fun `strings with brackets` () = Stream.of(
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
        fun `matching opening and closing chars`()= Stream.of(
            Arguments.of(
                "+++++ +++               Set Cell #0 to 8\n" +
                        " 2 [" , 9
            ),
            Arguments.of(
                "[<]", 3

        )
        )
        @JvmStatic
        fun `valid and invalid chars`()= Stream.of(
            Arguments.of(
                '+' , true),
            Arguments.of(
                '>', true),
            Arguments.of(
                'f', false
        )
        )

        @JvmStatic
        fun `complementary brackets`()= Stream.of(
            Arguments.of(
                '<' , '>'),
            Arguments.of(
                '.', ','),
            Arguments.of(
                '-', '+'),
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
        }

    @ParameterizedTest
    @MethodSource("matching opening and closing chars")
    fun `Count opening and closing symbols`(actualInput: String, expected: Int){
        var numberOfCharsInInput = newBaseClass.cleanedUpVariable(actualInput)
        assertEquals(expected, numberOfCharsInInput.count())
    }

    @ParameterizedTest
    @MethodSource("valid and invalid chars")
    fun `Does enum class contain character`(actualInput: Char, expected: Boolean){
        var enumContainsChar = newBaseClass.enumClassContains(actualInput)
        assertEquals(expected, enumContainsChar)
    }

    @Disabled
    @MethodSource("data for testing interpreter")
    fun `Test correct output after interpreting`(expected: String, actualOutput: String){
       assertEquals( "Here will be test Objects for interpreting", actualOutput)
    }

    @ParameterizedTest
    @MethodSource("strings with brackets")
    fun `Everything between actual BrainFuck chars should be removed`(actualInput: String, expected: String){
        var cleanedUpString = newBaseClass.cleanedUpVariable(actualInput)
        assertEquals(expected, cleanedUpString)
    }

    @ParameterizedTest
    @MethodSource("complementary brackets")
    fun `return complementary value to given Bracket`(actualInput: Char, expected: Char){
        var complementaryValue = newBaseClass.getComplementaryPairValue(actualInput)
        assertEquals(expected, complementaryValue)
    }

    @ParameterizedTest
    @MethodSource("small commands")
    fun `Check if command is valid`(actualInput: String, expectedStackValue: Boolean){
        newBaseClass.addNewCommand(actualInput)
        assertEquals(expectedStackValue, newBaseClass.isStackValid)
    }
}