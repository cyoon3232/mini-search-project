import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TokenizerTest {

    private final Tokenizer tokenizer = new Tokenizer();

    @Test 
    void tokenizesNormalSentence() {
        List<String> actual = tokenizer.tokenize("Hello World");
        List<String> expected = List.of("hello" , "world");
        assertEquals(expected, actual);
    }

    @Test 
    void tokenizesOneWord() {
        List<String> actual = tokenizer.tokenize("Hello");
        List<String> expected = List.of("hello");
        assertEquals(expected, actual);
    }

    @Test 
    void tokenizesAllLowercase() {
        List<String> actual = tokenizer.tokenize("hElLo TO EVeryOne");
        List<String> expected = List.of("hello", "to", "everyone");
        assertEquals(expected, actual);
    }
    
    @Test 
    void tokenizesRepeatedSpaces() {
        List<String> actual = tokenizer.tokenize(" Hello     World  ");
        List<String> expected = List.of("hello" , "world");
        assertEquals(expected, actual);
    }

    @Test 
    void tokenizesNewLinesAndTabs() {
        List<String> actual = tokenizer.tokenize("\tHello\nWorld \t\tAgain\n");
        List<String> expected = List.of("hello" , "world", "again");
        assertEquals(expected, actual);
    }

    @Test
    void tokenizesGeneralPunctuations() {
        List<String> actual = tokenizer.tokenize("Hello, Wo/rld! Java;Python-HTML??!");
        List<String> expected = List.of("hello", "wo", "rld", "java", "python", "html");
        assertEquals(expected, actual);
    }

    @Test 
    void tokenizesSurroundingApostrophe() {
        List<String> actual = tokenizer.tokenize("'We' said '  ' and 'Hello world!'");
        List<String> expected = List.of("we", "said", "and", "hello", "world");
        assertEquals(expected, actual);
    }

    @Test 
    void tokenizesInternalApostrophe() {
        List<String> actual = tokenizer.tokenize("We've said 'can't and shouldn't!'");
        List<String> expected = List.of("we've", "said", "can't", "and", "shouldn't");
        assertEquals(expected, actual);
    }

    @Test 
    void tokenizesNumbers() {
        List<String> actual = tokenizer.tokenize("It is September 7th, 2026 today.");
        List<String> expected = List.of("it", "is", "september", "7th", "2026", "today");
        assertEquals(expected, actual);
    }

    @Test 
    void tokenizesEmpty() {
        List<String> actual = tokenizer.tokenize("");
        List<String> expected = List.of();
        assertEquals(expected, actual);
    }

    @Test 
    void exceptionNull() {
        assertThrows(IllegalArgumentException.class, () -> tokenizer.tokenize(null));
    }

    @Test 
    void tokenizesSpacesOnly() {
        List<String> actual = tokenizer.tokenize("       ");
        List<String> expected = List.of();
        assertEquals(expected, actual);
    }

    @Test 
    void tokenizesPunctuationOnly() {
        List<String> actual = tokenizer.tokenize("?/'--\")({}+=!;");
        List<String> expected = List.of();
        assertEquals(expected, actual);
    }
}
