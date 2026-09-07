import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchEngine {

    private final Tokenizer tokenizer;
    private final InvertedIndex invertedIndex;
    private final Map<Integer, Document> documents;

    public SearchEngine(Tokenizer tokenizer, InvertedIndex invertedIndex) {
        this.tokenizer = tokenizer;
        this.invertedIndex = invertedIndex;
        documents = new HashMap<>();
    }

    /**
     * Adds a document to the collection that will be searched
     * @param document the document to index
     * @throws IllegalArgumentException if document is null or has an ID that already exists
     */
    public void index(Document document) {

    }

    /**
     * Searches the indexed documents for the given query
     * 
     * @param query non-null and non-blank query to evaluate
     * @param limit the maximum number of results to return; at least 1
     * @return matching documents ordered from highest to lowest in terms of relevance score;
     *         an empty list if no documents match
     * @throws IllegalArgumentException if the query is null or blank, or if limit is less than 1
     */
    public List<SearchResult> search(String query, int limit) {
        return null;
    }
    
}
