package org.example.parser;

import org.example.entity.Lexeme;
import org.example.entity.Sentence;
import org.example.entity.TextComponent;
import org.example.exception.TextProcessingException;
import org.example.util.RegexPatterns;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class LexemeParser extends AbstractTextParser {
    public List<Lexeme> parse(String sentence) {
        String normalized = sentence.strip().replaceAll(RegexPatterns.WHITESPACE_RUN, " ");
        if (normalized.isEmpty()) {
            throw new TextProcessingException("Cannot parse an empty sentence");
        }
        return Arrays.stream(normalized.split(RegexPatterns.LEXEME_SEPARATOR))
                .map(Lexeme::new)
                .toList();
    }
}