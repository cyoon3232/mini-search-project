import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DocumentTest {
    @Test
    void createsDocumentValid() {
        Document doc = new Document(1, "Java", "Java is a programming language.");
        assertEquals(1, doc.getId());
        assertEquals("Java", doc.getTitle());
        assertEquals("Java is a programming language.", doc.getContent());
    }

    @Test
    void emptyContentValid() {
        Document doc = new Document(2, "Empty", "");
        assertEquals(2, doc.getId());
        assertEquals("Empty", doc.getTitle());
        assertEquals("", doc.getContent());
    }

    @Test
    void idLessThanZero() {
        try {
            Document doc = new Document(-1, "Java", "content");
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println("Id is less than zero");
        }
    }

    @Test
    void idZero() {
        Document doc = new Document(0, "Java", "content");
        assertEquals(0, doc.getId());
        assertEquals("Java", doc.getTitle());
        assertEquals("content", doc.getContent());
    }

    @Test
    void nullTitle() {
        try {
            Document doc = new Document(0, null, "Null");
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println("Title is null");
        }
    }

    @Test
    void blankTitle() {
        try {
            Document doc = new Document(0, "", "Null");
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println("Title is blank");
        }
    }

    @Test
    void nullContent() {
        try {
            Document doc = new Document(0, "Null", null);
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println("Content is null");
        }
    }

}
