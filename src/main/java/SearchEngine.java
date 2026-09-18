import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchEngine {

    private final Tokenizer tokenizer;
    private final InvertedIndex invertedIndex;
    private final Map<Integer, Document> documents;

    private static final Comparator<SearchResult> RESULT_ORDER = Comparator.comparingDouble(SearchResult::getScore).reversed()
                                                                .thenComparing(SearchResult::getDocumentId);

    /**
     * Creates a search engine using a tokenizer and inverted index
     * @param tokenizer used to normalize document
     * @param invertedIndex used to store and retrieve term occrurences
     * @throws IllegalArgumentException if tokenizer is null or invertedIndex is null
     */
    public SearchEngine(Tokenizer tokenizer, InvertedIndex invertedIndex) {
        if (tokenizer == null) {
            throw new IllegalArgumentException();
        }

        if (invertedIndex == null) {
            throw new IllegalArgumentException();
        }

        this.tokenizer = tokenizer;
        this.invertedIndex = invertedIndex;
        this.documents = new HashMap<>();
    }

    /**
     * Indexes a document's content and stores the document for later retrieval
     * @param document the document to index
     * @throws IllegalArgumentException if document is null or has an ID that has already been indexed
     */
    public void index(Document document) {
        if (document == null) {
            throw new IllegalArgumentException();
        }

        if (documents.containsKey(document.getId())) {
            throw new IllegalArgumentException();
        }

        List<String> tokens = tokenizer.tokenize(document.getContent());
        invertedIndex.addDocument(document.getId(), tokens);

        documents.put(document.getId(), document);
    }

    /**
     * Searches the indexed documents for the given query
     * @param query non-null and non-blank query to evaluate
     * @param limit the maximum number of results to return; at least 1
     * @return matching documents ordered from highest to lowest in terms of relevance score;
     *         an empty list if no documents match
     * @throws IllegalArgumentException if the query is null or blank, or if limit is less than 1
     */
    public List<SearchResult> search(String query, int limit) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (limit < 1) {
            throw new IllegalArgumentException();
        }

        List<String> queryTokens = tokenizer.tokenize(query);
        if (queryTokens.isEmpty()) {
            return List.of();
        }

        Set<String> distinctQueryTokens = new HashSet<>(queryTokens);
        Map<Integer, Double> scores = new HashMap<>();

        for (String term : distinctQueryTokens) {
            Map<Integer, List<Integer>> postings = invertedIndex.getPostings(term);

            for (var entry : postings.entrySet()) {
                int documentId = entry.getKey();
                double score = entry.getValue().size();

                if (scores.containsKey(documentId)) {
                    score += scores.get(documentId);
                }

                scores.put(documentId, score);
            }
        }

        List<SearchResult> results = new ArrayList<>();
        for (var entry : scores.entrySet()) {
            SearchResult result = new SearchResult(entry.getKey(), entry.getValue());
            results.add(result);
        }
        
        results.sort(RESULT_ORDER);

        int resultCount = Math.min(limit, results.size());

        List<SearchResult> resultList = new ArrayList<>(results.subList(0, resultCount));
        return resultList;
    }
    
}
