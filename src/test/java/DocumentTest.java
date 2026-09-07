import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class DocumentTest {
    @Test
    void createsValidDocument() {
        Document doc = new Document(1, "Java", "Java is a programming language.");
        assertEquals(1, doc.getId());
        assertEquals("Java", doc.getTitle());
        assertEquals("Java is a programming language.", doc.getContent());
    }

    @Test
    void createsValidEmptyContent() {
        Document doc = new Document(2, "Empty", "");
        assertEquals(2, doc.getId());
        assertEquals("Empty", doc.getTitle());
        assertEquals("", doc.getContent());
    }

    @Test
    void exceptionIdLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> new Document(-1, "Java", ""));
    }

    @Test
    void createsValidIdZero() {
        Document doc = new Document(0, "Java", "content");
        assertEquals(0, doc.getId());
        assertEquals("Java", doc.getTitle());
        assertEquals("content", doc.getContent());
    }

    @Test
    void exceptionNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Document(0, null, "null"));
    }

    @Test
    void exceptionEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Document(0, "", "null"));
    }

    @Test
    void exceptionBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Document(0, "          ", "null"));
    }

    @Test
    void exceptionNullContent() {
        assertThrows(IllegalArgumentException.class, () -> new Document(0, "Null", null));
    }

}
