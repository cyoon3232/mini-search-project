import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Reference: chatgpt
public class SearchEngineSearchTest {

    private SearchEngine searchEngine;

    @BeforeEach
    void setUp() {
        Tokenizer tokenizer = new Tokenizer();
        InvertedIndex invertedIndex = new InvertedIndex();
 
        searchEngine = new SearchEngine(tokenizer, invertedIndex);
 
        searchEngine.index(new Document(1, "Document 1", "java java python"));
        searchEngine.index(new Document(2, "Document 2", "python python"));
        searchEngine.index(new Document(3, "Document 3", "java"));
        searchEngine.index(new Document(4, "Document 4", "rust"));
        searchEngine.index(new Document(7, "Document 7", "rust"));
    }

    @Test
    void searchesSingleTerm() {
        List<SearchResult> results = searchEngine.search("java", 10);

        assertEquals(2, results.size());

        assertEquals(1, results.get(0).getDocumentId());
        assertEquals(2.0, results.get(0).getScore());

        assertEquals(3, results.get(1).getDocumentId());
        assertEquals(1.0, results.get(1).getScore());
    }

    @Test
    void searchesMultipleTermsUsingOrSemantics() {
        List<SearchResult> results = searchEngine.search("java python", 10);

        assertEquals(3, results.size());

        assertEquals(1, results.get(0).getDocumentId());
        assertEquals(3.0, results.get(0).getScore());

        assertEquals(2, results.get(1).getDocumentId());
        assertEquals(2.0, results.get(1).getScore());

        assertEquals(3, results.get(2).getDocumentId());
        assertEquals(1.0, results.get(2).getScore());
    }

    @Test
    void normalizesQueryBeforeSearching() {
        List<SearchResult> results = searchEngine.search("JAVA", 10);

        assertEquals(2, results.size());

        assertEquals(1, results.get(0).getDocumentId());
        assertEquals(2.0, results.get(0).getScore());

        assertEquals(3, results.get(1).getDocumentId());
        assertEquals(1.0, results.get(1).getScore());
    }

    @Test
    void mixedCaseQueryWithMultipleTermsMatchesConsistently() {
        List<SearchResult> results = searchEngine.search("JaVa PyThOn", 10);

        assertEquals(3, results.size());
        assertEquals(1, results.get(0).getDocumentId());
        assertEquals(3.0, results.get(0).getScore());
    }

    @Test
    void duplicateQueryTermsAreNotDoubleCounted() {
        List<SearchResult> results = searchEngine.search("java java java", 10);

        assertEquals(2, results.size());

        assertEquals(1, results.get(0).getDocumentId());
        assertEquals(2.0, results.get(0).getScore());

        assertEquals(3, results.get(1).getDocumentId());
        assertEquals(1.0, results.get(1).getScore());
    }

    @Test
    void ordersEqualScoresByDocumentIdAscending() {
        List<SearchResult> results = searchEngine.search("rust", 10);

        assertEquals(2, results.size());

        assertEquals(4, results.get(0).getDocumentId());
        assertEquals(1.0, results.get(0).getScore());

        assertEquals(7, results.get(1).getDocumentId());
        assertEquals(1.0, results.get(1).getScore());
    }

    @Test
    void limitRestrictsNumberOfResults() {
        List<SearchResult> results = searchEngine.search("java python", 2);
        assertEquals(2, results.size());

        assertEquals(1, results.get(0).getDocumentId());
        assertEquals(3.0, results.get(0).getScore());

        assertEquals(2, results.get(1).getDocumentId());
        assertEquals(2.0, results.get(1).getScore());
    }

    @Test
    void limitLargerThanNumberOfMatchesReturnsAllMatches() {
        List<SearchResult> results = searchEngine.search("java python", 100);
        assertEquals(3, results.size());
    }

    @Test
    void notFoundTermReturnsEmptyList() {
        List<SearchResult> results = searchEngine.search("javascript", 10);

        assertTrue(results.isEmpty());
    }

    @Test
    void punctuationOnlyQueryReturnsEmptyList() {
        List<SearchResult> results = searchEngine.search("!!!???---", 10);

        assertTrue(results.isEmpty());
    }

    @Test
    void nullQueryThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> searchEngine.search(null, 10)
        );
    }

    @Test
    void emptyQueryThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> searchEngine.search("", 10)
        );
    }

    @Test
    void blankQueryThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> searchEngine.search("       ", 10)
        );
    }

    @Test
    void zeroLimitThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> searchEngine.search("java", 0)
        );
    }

    @Test
    void negativeLimitThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> searchEngine.search("java", -1)
        );
    }
}
