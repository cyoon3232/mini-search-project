import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvertedIndex {

    private final Map<String, Map<Integer, List<Integer>>> index = new HashMap<>();

    /**
     * adds the normalized and ordered tokens of a document to its index according to its position in the document
     * @param documentId the id of document is >= 0
     * @param tokens the normalized tokenized text in their original document order
     * @throws IllegalArgumentException if documentId < 0, tokens is null, or at least one token is null or blank
     */
    public void addDocument(int documentId, List<String> tokens) {
        if (documentId < 0) {
            throw new IllegalArgumentException();
        }

        if (tokens == null) {
            throw new IllegalArgumentException();
        }

        for (String token : tokens) {
            if (token == null || token.isBlank()) {
                throw new IllegalArgumentException();
            }
        }

        for (int position = 0; position < tokens.size(); position++) {
            String currentToken = tokens.get(position);
            addOccurrence(currentToken, documentId, position);
        }
    }

    private void addOccurrence(String term, int documentId, int position) {
        Map<Integer, List<Integer>> currentMap = index.computeIfAbsent(term, termKey -> new HashMap<>());
        List<Integer> currentList = currentMap.computeIfAbsent(documentId, docId -> new ArrayList<>());
        currentList.add(position);
    }

    /**
     * returns the document IDs and token positions associated with a normalized term
     * @param term the term searched for
     * @return copy of postings; empty if the term doesn't exist in map
     * @throws IllegalArgumentException if term is null or blank
     */
    public Map<Integer, List<Integer>> getPostings(String term) {
        if (term == null || term.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (!index.containsKey(term)) {
            return new HashMap<>();
        }

        Map<Integer, List<Integer>> currentMap = index.get(term);
        Map<Integer, List<Integer>> deepCopy = new HashMap<>();
        for (var entry : currentMap.entrySet()) {
            Integer key = entry.getKey();
            deepCopy.put(key, new ArrayList<>(entry.getValue()));
        }
        return deepCopy;
    }
}
