import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    List<String> texts;
    int index;
    boolean consecutiveBlanks;

    /**
     * Converts text into normalized searchable tokens while keeping token order
     * Tokens are converted to lowercase. Numbers are preserved. All punctuation
     * except apostrophes within words separates tokens.
     * @param text the text to tokenize
     * @return the normalized tokens in their original order;
     *         an empty list if the text contains no tokens
     * @throws IllegalArgumentException if text is null
     */
    public List<String> tokenize(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        texts = new ArrayList<>();
        texts.add("");

        while (text.length() > 1) {
            tokenizeEach(text.substring(0, 1));
            text = text.substring(1);
        }
        tokenizeEach(text);

        return texts;
    }

    private void tokenizeEach(String c) {
        c.toLowerCase();

        String current = texts.get(index);
        // use LetterOrDigit
        boolean isPunctuation = c.equals(",") 
                                || current.equals(".") 
                                || current.equals("!") 
                                || current.equals("?")
                                || current.equals("-")
                                || current.equals("_");

        if (c.isBlank()) {
            if (!current.isEmpty()) {
                index++;
            }
            return;
        }

        if (isPunctuation) {
            if (!current.isEmpty()) {
                index++;
            }
            return;
        }

        if (!current.isEmpty()) {
            c = current + c;
            texts.add(index, c);
        } else {
            texts.add(index, c);
        }
    }

}
