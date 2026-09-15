import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvertedIndexTest {

    InvertedIndex index;

    @BeforeEach 
    void setUp() {
        index = new InvertedIndex();
    }

    @Test
    void indexRepeatedTerm() {
        index.addDocument(7, List.of("java", "is", "great", "java"));

        Map<Integer, List<Integer>> expectedFromTermJava = Map.of(7, List.of(0, 3));

        assertEquals(expectedFromTermJava, index.getPostings("java"));    
    }

    @Test 
    void indexDifferentTerms() {
        index.addDocument(7, List.of("java", "is", "great", "java"));

        assertEquals(Map.of(7, List.of(1)), index.getPostings("is"));
        assertEquals(Map.of(7, List.of(2)), index.getPostings("great"));
    }

    @Test 
    void indexSameTermAcrossMultipleDocuments() {
        index.addDocument(3, List.of("java", "rocks"));
        index.addDocument(8, List.of("learn", "java", "java"));

        Map<Integer, List<Integer>> expectedFromTermJava = Map.of(3, List.of(0), 8, List.of(1, 2));
        assertEquals(expectedFromTermJava, index.getPostings("java"));
    }
    
    @Test 
    void allowsEmptyTokenList() {
        assertDoesNotThrow(() -> index.addDocument(4, List.of()));
    }

    @Test 
    void exceptionNegativeDocumentId() {
        assertThrows(IllegalArgumentException.class, () -> index.addDocument(-1, List.of("java")));
    }

    @Test 
    void exceptionNullTokenList() {
        assertThrows(IllegalArgumentException.class, () -> index.addDocument(1, null));
    }

    @Test 
    void exceptionNullToken() {
        List<String> tokens = new ArrayList<>();
        tokens.add("java");
        tokens.add(null);
        tokens.add("search");
        assertThrows(IllegalArgumentException.class, () -> index.addDocument(1, tokens));
    }

    @Test 
    void exceptionBlankToken() {
        List<String> tokens = List.of("java", "", "search");
        assertThrows(IllegalArgumentException.class, () -> index.addDocument(1, tokens));
    }

    @Test 
    void invalidDocumentCannotModifyIndex() {
        List<String> tokens = List.of("java", "search", "");
        assertThrows(IllegalArgumentException.class, () -> index.addDocument(1, tokens));
        assertTrue(index.getPostings("java").isEmpty());
        assertTrue(index.getPostings("search").isEmpty());
    }

    @Test 
    void returnEmptyMapForNotFoundTerm() {
        Map<Integer, List<Integer>> postings = index.getPostings("nothing");
        assertTrue(postings.isEmpty());
    }

    @Test 
    void exceptionGetPostingsForNull() {
        assertThrows(IllegalArgumentException.class, () -> index.getPostings(null));
    }

    @Test 
    void exceptionGetPostingsForEmptyTerm() {
        assertThrows(IllegalArgumentException.class, () -> index.getPostings(""));
    }

    @Test 
    void exceptionGetPostingsForBlankTerm() {
        assertThrows(IllegalArgumentException.class, () -> index.getPostings("     "));
    }

    @Test
    void returnedPostingsMapIsDeepCopy() {
        index.addDocument(7, List.of("java", "java"));

        Map<Integer, List<Integer>> returned = index.getPostings("java");
        Map<Integer, List<Integer>> expected = Map.of(7, List.of(0, 1));

        assertEquals(expected, returned);

        returned.clear();

        assertNotEquals(expected, returned);
        assertEquals(expected, index.getPostings("java"));
    }

    @Test 
    void returnedPostingsMapListIsDeepCopy() {
        index.addDocument(7, List.of("java", "java"));

        Map<Integer, List<Integer>> returned = index.getPostings("java");
        returned.get(7).clear();

        Map<Integer, List<Integer>> expected = Map.of(7, List.of(0, 1));

        assertNotEquals(expected, returned);
        assertEquals(expected, index.getPostings("java"));
    }
}
