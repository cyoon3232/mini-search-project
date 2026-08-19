public class SearchResult {
    
    private final int documentId;
    private final double score;

    public SearchResult(int documentId, double score) {
        this.documentId = documentId;
        this.score = score;
    }
    
    public int getDocumentId() {
        return documentId;
    }


    public double getScore() {
        return score;
    }
    
}
