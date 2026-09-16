import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchEngineTest {
    
    private Tokenizer tokenizer;
    private InvertedIndex invertedIndex;
    private SearchEngine searchEngine;

    @BeforeEach 
    void setUp() {
        tokenizer = new Tokenizer();
        invertedIndex = new InvertedIndex();
        searchEngine = new SearchEngine(tokenizer, invertedIndex);
    }

    @Test 
    void indexCheckInvertedIndexOneDocument() {
        Document document = new Document(1, "Java", "Java is a useful language. Java is very popular, too.");
        searchEngine.index(document);

        Map<Integer, List<Integer>> expectedFromJava = Map.of(1, List.of(0, 5));
        Map<Integer, List<Integer>> expectedFromIs = Map.of(1, List.of(1, 6));
        Map<Integer, List<Integer>> expectedFromUseful = Map.of(1, List.of(3));
        Map<Integer, List<Integer>> expectedFromToo = Map.of(1, List.of(9));

        assertEquals(expectedFromJava, invertedIndex.getPostings("java"));
        assertEquals(expectedFromIs, invertedIndex.getPostings("is"));
        assertEquals(expectedFromUseful, invertedIndex.getPostings("useful"));
        assertEquals(expectedFromToo, invertedIndex.getPostings("too"));
    }

    @Test 
    void indexCheckInvertedIndexMultipleDocuments() {
        Document document1 = new Document(1, "Java", "Java is useful. Java is popular.");
        Document document2 = new Document(2, "Python", "Python is a very popular language, too.");

        searchEngine.index(document1);
        searchEngine.index(document2);

        Map<Integer, List<Integer>> expectedFromJava = Map.of(1, List.of(0, 3));
        Map<Integer, List<Integer>> expectedFromIs = Map.of(1, List.of(1, 4), 2, List.of(1));
        Map<Integer, List<Integer>> expectedFromLanguage = Map.of(2, List.of(5));
        Map<Integer, List<Integer>> expectedFromPopular = Map.of(1, List.of(5), 2, List.of(4));

        assertEquals(expectedFromJava, invertedIndex.getPostings("java"));
        assertEquals(expectedFromIs, invertedIndex.getPostings("is"));
        assertEquals(expectedFromLanguage, invertedIndex.getPostings("language"));
        assertEquals(expectedFromPopular, invertedIndex.getPostings("popular"));
    }

    @Test 
    void indexEmptyContentDocument() {
        Document document = new Document(0, "Empty", "");
        searchEngine.index(document);

        Map<Integer, List<Integer>> expectedFromJava = Map.of();
        Map<Integer, List<Integer>> expectedFromIs = Map.of();

        assertEquals(expectedFromJava, invertedIndex.getPostings("java"));
        assertEquals(expectedFromIs, invertedIndex.getPostings("is"));
    }

    @Test 
    void exceptionIndexNullDocument() {
        assertThrows(IllegalArgumentException.class, () -> searchEngine.index(null));
    }

    @Test 
    void exceptionIndexDuplicateDocumentId() {
        Document document = new Document(1, "Java", "This is java.");
        Document duplicateId = new Document(1, "Python", "This is python.");
        searchEngine.index(document);

        assertThrows(IllegalArgumentException.class, () -> searchEngine.index(duplicateId));

        Map<Integer, List<Integer>> expectedFromJava = Map.of(1, List.of(2));
        Map<Integer, List<Integer>> expectedFromPython = Map.of();

        assertEquals(expectedFromJava, invertedIndex.getPostings("java"));
        assertEquals(expectedFromPython, invertedIndex.getPostings("python"));
    }

    @Test 
    void exceptionConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> new SearchEngine(null, new InvertedIndex()));
        assertThrows(IllegalArgumentException.class, () -> new SearchEngine(new Tokenizer(), null));
    }
}
