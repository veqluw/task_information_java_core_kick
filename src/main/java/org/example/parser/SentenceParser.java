package org.example.parser;

import org.example.entity.Paragraph;
import org.example.entity.Sentence;
import org.example.entity.TextComponent;
import org.example.exception.TextProcessingException;
import org.example.util.RegexPatterns;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class SentenceParser extends AbstractTextParser {
    private final LexemeParser lexemeParser;

    public SentenceParser(LexemeParser lexemeParser) {
        this.lexemeParser = lexemeParser;
    }

    public List<Sentence> parse(String paragraph) {
        String normalized = paragraph.strip().replaceAll(RegexPatterns.WHITESPACE_RUN, " ");
        if (normalized.isEmpty()) {
            throw new TextProcessingException("Cannot parse an empty paragraph");
        }
        String[] parts = normalized.split(RegexPatterns.SENTENCE_BOUNDARY);
        return Arrays.stream(parts)
                .map(String::strip)
                .filter(part -> !part.isEmpty())
                .map(part -> new Sentence(lexemeParser.parse(part)))
                .toList();
    }
}