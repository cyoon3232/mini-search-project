import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class InvertedIndex {

    private final Map<String, Map<Integer, List<Integer>>> index = new HashMap<>();
    
    /**
     * documentId < 0, tokens == null, token == null or isBlank
     * this is to check
     */

    /**
     * adds the normalized and ordered tokens of a document to its index according to its position in the document
     * @param documentId the id of document is >= 0
     * @param tokens the normalized tokenized text in their original document order
     * @throws IllegalArgumentException if documentId < 0, tokens is null, or at least one token is null or blank
     */
    public void addDocument(int documentId, List<String> tokens) {
        // documentid tokens token exception
        // loop each tokens
    }

    private void addOccurrence(String term, int documentId, int position) {
        // does term exist?
        // no -> create new inner map
        // yes -> existing map

        // documentid exist for the term?
        // no -> create new List inside the map
        // yes -> append to existing List
    }

    /**
     * 
     * @param term searched for
     * @return copy of postings; empty if the term doesn't exist in map
     * @throws IllegalArgumentException if term is null or blank
     */
    public Map<Integer, List<Integer>> getPostings(String term) {
        // term exception
        // term exist?
        // no -> return empty
        // yes -> make a copy and return
        return null;
    }
}
