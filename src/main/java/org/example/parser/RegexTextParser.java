package org.example.parser;

import org.example.entity.Paragraph;
import org.example.entity.Text;
import org.example.exception.TextProcessingException;

import java.util.List;

public final class RegexTextParser implements TextParser {
    private final ParagraphParser paragraphParser;

    public RegexTextParser() {
        LexemeParser lexemeParser = new LexemeParser();
        SentenceParser sentenceParser = new SentenceParser(lexemeParser);
        this.paragraphParser = new ParagraphParser(sentenceParser);
    }

    @Override
    public Text parse(String source) {
        if (source == null || source.isBlank()) {
            throw new TextProcessingException("Input text must not be null or blank");
        }
        List<Paragraph> paragraphs = paragraphParser.parse(source);
        return new Text(paragraphs);
    }
}
