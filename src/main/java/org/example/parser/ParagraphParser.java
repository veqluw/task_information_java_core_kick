package org.example.parser;

import org.example.entity.Paragraph;
import org.example.entity.Text;
import org.example.entity.TextComponent;
import org.example.exception.TextProcessingException;
import org.example.util.RegexPatterns;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class ParagraphParser extends AbstractTextParser {
    private final SentenceParser sentenceParser;

    public ParagraphParser(SentenceParser sentenceParser) {
        this.sentenceParser = sentenceParser;
    }

    public List<Paragraph> parse(String source) {
        String normalized = source.replace("\r\n", "\n").replace('\r', '\n').strip();
        if (normalized.isEmpty()) {
            throw new TextProcessingException("Input text is empty");
        }
        String[] blocks = normalized.split(RegexPatterns.PARAGRAPH_SEPARATOR);
        return Arrays.stream(blocks)
                .map(String::strip)
                .filter(block -> !block.isEmpty())
                .map(block -> new Paragraph(sentenceParser.parse(block)))
                .toList();
    }
}
