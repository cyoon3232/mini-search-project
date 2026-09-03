import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

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

        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            boolean isPunctuation = !Character.isLetterOrDigit(c);

            if (Character.isWhitespace(c) || isPunctuation) {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString().toLowerCase());
                    currentToken.setLength(0);
                }
            } else {
                currentToken.append(c);
            }
        }
        return tokens;

    }

}
