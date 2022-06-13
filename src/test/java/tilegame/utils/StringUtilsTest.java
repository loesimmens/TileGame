package tilegame.utils;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StringUtilsTest {

    final int maxTokensPerLine = 10;

    @ParameterizedTest
    @CsvSource(value = {"hello,hello", "hello ,hello", "hello you multiple lines,'hello you\nmultiple\nlines'", "Text bla. Bladiebla bla.,'Text bla.\nBladiebla\nbla.'"})
    void cutTextToFitLineTest(String text, String expected) {
        String result = StringUtils.cutTextToFitLine(text, maxTokensPerLine);
        assertEquals(expected, result);
    }
}