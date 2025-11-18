package traineeship_manager.recomendstrategies;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void testNormalInput() {
        String input = "driving , English , baking , coOking,maths,computer science";
        List<String> expected = List.of("driving", "english", "baking", "cooking", "maths", "computer science");

        List<String> result = StringUtil.parseStringToList(input);
        assertEquals(expected, result);
    }

    @Test
    void testInputWithExtraSpaces() {
        String input = "  java ,  Python  ,c++ ";
        List<String> expected = List.of("java", "python", "c++");

        List<String> result = StringUtil.parseStringToList(input);
        assertEquals(expected, result);
    }

    @Test
    void testEmptyInput() {
        String input = "";
        List<String> result = StringUtil.parseStringToList(input);
        assertTrue(result.isEmpty());
    }

    @Test
    void testNullInput() {
        String input = null;
        List<String> result = StringUtil.parseStringToList(input);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSingleElement() {
        String input = " Math ";
        List<String> expected = List.of("math");
        List<String> result = StringUtil.parseStringToList(input);
        assertEquals(expected, result);
    }
}
