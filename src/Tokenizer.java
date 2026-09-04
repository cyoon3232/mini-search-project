import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

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
            boolean isPunctuationOrWhitespace = !Character.isLetterOrDigit(c);

            if (isPunctuationOrWhitespace) {
                if (c == '\'' && isInternalApostrophe(i, text)) { // TODO: add unicode curly apostrophes
                    currentToken.append(c);
                } else if (currentToken.length() > 0) {
                    addToTokensAndResetCurrentToken(tokens, currentToken);
                }
            } else {
                currentToken.append(c);
            }
        }
        if (currentToken.length() > 0) {
            addToTokensAndResetCurrentToken(tokens, currentToken);
        }
        
        return tokens;
    }

    private void addToTokensAndResetCurrentToken(List<String> tokens, StringBuilder currentToken) {
        tokens.add(currentToken.toString().toLowerCase());
        currentToken.setLength(0);
    }

    private boolean isInternalApostrophe(int i, String text) {
        if (i <= 0 || i >= text.length() - 1) {
            return false;
        } 
        boolean isCharBeforeValid = Character.isLetterOrDigit(text.charAt(i - 1));
        boolean isCharAfterValid = Character.isLetterOrDigit(text.charAt(i + 1));
        return isCharBeforeValid && isCharAfterValid;
    }

}
