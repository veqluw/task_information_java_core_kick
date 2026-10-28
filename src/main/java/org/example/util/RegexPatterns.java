package org.example.util;

public final class RegexPatterns {
    public static final String PARAGRAPH_SEPARATOR = "(?:\\R\\s*){2,}";
    public static final String SENTENCE_BOUNDARY = "(?<=[.!?])\\s+";
    public static final String LEXEME_SEPARATOR = "\\s+";
    public static final String WHITESPACE_RUN = "[\\t\\x0B\\f ]+";
    public static final String LETTER = "\\p{L}";
    public static final String WORD = "[\\p{L}\\p{M}]+(?:['’\\-][\\p{L}\\p{M}]+)*";

    private RegexPatterns() {
        throw new AssertionError("No instances");
    }
}
