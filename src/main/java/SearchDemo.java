import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

// TODO: add quit
public class SearchDemo {

    private static Map<Integer, Document> documents;

    public static void main(String[] args) {
        documents = new HashMap<>();
        
        Tokenizer tokenizer = new Tokenizer();
        InvertedIndex invertedIndex = new InvertedIndex();
        SearchEngine searchEngine = new SearchEngine(tokenizer, invertedIndex);

        Document sample1 = new Document(
            0, 
            "Java", 
            "Java is a popular programming language. It is widely used for "
            + "backend development, Android app development and cloud computing."
        );
        Document sample2 = new Document(
            1, 
            "UBC", 
            "The University of British Columbia, aka UBC, is a public research " 
            + "university in British Columbia, Canada."
        );
        Document sample3 = new Document(
            2,
            "Python",
            "Python is a programming language commonly used for "
            + "data science, machine learning, automation, and backend development."
        );
        Document sample4 = new Document(
            3,
            "Search Engines",
            "Search engines index documents so users can search for information. "
            + "Search results can be ranked according to relevance."
        );

        addDocumentToSystem(sample1, searchEngine);
        addDocumentToSystem(sample2, searchEngine);
        addDocumentToSystem(sample3, searchEngine);
        addDocumentToSystem(sample4, searchEngine);

        // Console
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        System.out.println();
        System.out.println("================================");
        System.out.println("       Java Search Platform");
        System.out.println("================================");
        System.out.println(documents.size() + " sample documents are already indexed.");

        while (running) {
            printMenu();

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    addDocuments(scanner, searchEngine);
                    break;

                case "2":
                    searchDocuments(scanner, searchEngine);
                    break;

                case "3":
                    listDocuments();
                    break;

                case "4":
                    running = false;
                    System.out.println();
                    System.out.println("Exiting Java Search Platform.");
                    break;

                default:
                    System.out.println();
                    System.out.println(
                            "Invalid option. Please enter 1, 2, 3, or 4."
                    );
            }
        }

        scanner.close();
        
    }

    //------------------MENU------------------------
    private static void printMenu() {
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println("1. Add documents");
        System.out.println("2. Search documents");
        System.out.println("3. List documents");
        System.out.println("4. Exit");
        System.out.println("--------------------------------");
        System.out.print("Choose an option: ");
    }

    //---------------ADD DOCUMENTS--------------------

    private static void addDocuments(Scanner scanner, SearchEngine searchEngine) {
        boolean addingDocuments = true;
        while (addingDocuments) {
            System.out.println();
            System.out.println("<---- Add Document ---->");
            int documentId = readUniqueDocumentId(scanner);

            String title = readDocumentTitle(scanner);

            System.out.print("Enter document content: ");
            String content = scanner.nextLine();

            Document document = new Document(documentId, title, content);

            addDocumentToSystem(document, searchEngine);

            System.out.println();
            System.out.println("Document " + documentId + " added and indexed successfully.");

            addingDocuments = readYesOrNo(scanner, "Would you like to add another document? (y/n): ");
        }
    }

    private static int readUniqueDocumentId(Scanner scanner) {
        while (true) {
            System.out.println("Existing document IDs: " + new TreeSet<>(documents.keySet()));
            System.out.print("Enter a non-negative document ID: ");

            String input = scanner.nextLine().trim();
            int documentId;
            try {
                documentId = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID. Please enter an integer.");
                continue;
            }

            if (documentId < 0) {
                System.out.println("Document ID cannot be negative.");
                continue;
            }

            if (documents.containsKey(documentId)) {
                System.out.println("Document ID " + documentId + " already exists.");
                System.out.println("Please choose a different ID.");
                continue;
            }

            return documentId;
        }
    }

    private static String readDocumentTitle(Scanner scanner) {
        while (true) {
            System.out.print("Enter document title: ");

            String title = scanner.nextLine();

            if (title.isBlank()) {
                System.out.println("Document title cannot be blank.");
                continue;
            }

            return title;
        }
    }

    //------------------SEARCH---------------------
    private static void searchDocuments(Scanner scanner, SearchEngine searchEngine) {
        System.out.println();
        System.out.println("<---- Search Documents ---->");

        boolean searchingDocuments = true;

        while (searchingDocuments) {
            String query = readSearchQuery(scanner);

            int inputLimit = readPositiveInteger(scanner, "Maximum number of results: ");

            List<SearchResult> results = searchEngine.search(query, inputLimit);

            printSearchResults(query, results);

            searchingDocuments = readYesOrNo(scanner, "Would you like to continue searching? (y/n): ");
        }
    }

    private static String readSearchQuery(Scanner scanner) {
        while (true) {
            System.out.print("Enter search query: ");
            String query = scanner.nextLine();
            if (query.isBlank()) {
                System.out.println("Search query cannot be blank.");
                continue;
            }
            return query;
        }
    }

    private static void printSearchResults(String query, List<SearchResult> results) {
        System.out.println();
        System.out.println("Search results for \"" + query + "\"");
        System.out.println("--------------------------------");

        if (results.isEmpty()) {
            System.out.println("No matching documents found.");
            return;
        }

        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            Document document = documents.get(result.getDocumentId());
            
            System.out.println((i + 1) + ". " + document.getTitle());
            System.out.println("   Document ID: " + document.getId());
            System.out.println("   Score: "  + result.getScore());
            System.out.println("   Content: "  + document.getContent());
            System.out.println();
        }
    }

    //--------------LIST DOCUMENTS-----------------

    private static void listDocuments() {
        System.out.println();
        System.out.println("<---- Indexed Documents ---->");

        if (documents.isEmpty()) {
            System.out.println("No documents are currently indexed.");
            return;
        }

        for (Integer documentId : new TreeSet<>(documents.keySet())) {
            Document document = documents.get(documentId);
            System.out.println();
            System.out.println("ID: " + document.getId());
            System.out.println("Title: " + document.getTitle());
            System.out.println("Content: " + document.getContent());
        }

        System.out.println();
        System.out.println("Total documents: " + documents.size());
    }


    //------------------HELPERS---------------------

    private static void addDocumentToSystem(Document document, SearchEngine searchEngine) {
        searchEngine.index(document);
        documents.put(document.getId(), document);
    }

    // Returns a postive number; re-reads when not
    private static int readPositiveInteger(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {
                int number = Integer.parseInt(input);
                if (number <= 0) {
                    System.out.println("Please enter an integer greater than 0.");
                    continue;
                }
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static boolean readYesOrNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y")) {
                return true;
            }

            if (input.equals("n")) {
                return false;
            }

            System.out.println("Please enter y or n.");
        }
    }
}