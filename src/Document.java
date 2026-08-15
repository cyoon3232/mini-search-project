public class Document {
    private int id;
    private String fileName;
    private String content;

    public Document(int id, String fileName, String content) {
        this.id = id;
        this.fileName = fileName;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

}