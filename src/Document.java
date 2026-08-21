public class Document {
    private final int id;
    private final String title;
    private final String content;

    public Document(int id, String title, String content) throws Exception {
        if (id >= 0 && title != null && content != null) {
            this.id = id;
            this.title = title;
            this.content = content;
        } else {
            throw new IllegalArgumentException();
        }
        
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