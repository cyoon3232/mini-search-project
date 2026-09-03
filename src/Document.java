public class Document {
    private final int id;
    private final String title;
    private final String content;
    
    /**
     * creates a document
     * @param id the id >= 0
     * @param title the title is not null and not blank
     * @param content the content is not null
     * @throws IllegalArgumentException if id < 0, title is blank or null, content is null
     */
    public Document(int id, String title, String content) {
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException();
        }
        if (content == null) {
            throw new IllegalArgumentException();
        }
        
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}