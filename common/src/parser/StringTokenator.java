package parser;

import java.util.regex.Pattern;

public class StringTokenator {

    private final String string;
    private final char deliminator;
    private final int stringLength;
    private int currentIndex = 0;
    private int tokenCount = 0;
    private int consumedTokenCount = 0;

    public StringTokenator(String string, char deliminator) {
        if (string == null) {
            throw new IllegalArgumentException(
                    "String to be tokenated cannot be null.");
        }
        this.string = string;
        this.deliminator = deliminator;
        this.stringLength = string.length();
        this.tokenCount = getTokenCount();
    }

    public char getDeliminator() {
        return this.deliminator;
    }

    public boolean hasMoreTokens() {
        return !(consumedTokenCount >= tokenCount);
    }

    public synchronized int getTokenCount() {
        if (tokenCount != 0) {
            return tokenCount;
        }

        for (int i = 0; i < stringLength; i++) {
            if (string.charAt(i) == deliminator) {
                tokenCount++;
            }
        }
        // to account for starting point of -1
        tokenCount = tokenCount + 1;

        return tokenCount;
    }

    public synchronized String nextToken() {
        if (hasMoreTokens() == false) {
            return null;
        }
        StringBuilder token = new StringBuilder();
        for (int i = currentIndex; i < stringLength; i++) {
            char currentChar = string.charAt(i);
            if (currentChar == deliminator) {
                currentIndex = i + 1;
                break;
            }
            token.append(currentChar);
        }
        return token.toString();
    }

    public String[] tokenate() {
        Pattern pattern = Pattern.compile(String.valueOf(deliminator));
        return pattern.split(string, -1);
    }

}
