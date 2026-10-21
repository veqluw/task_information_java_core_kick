package org.example.parser;

import org.example.entity.Paragraph;
import org.example.entity.Sentence;
import org.example.entity.TextComponent;

import java.util.regex.Pattern;

public class SentenceParser extends AbstractTextParser {

    private static final String SENTENCE_REGEX = "(?<=[.!?])\\s+";
    private static final Pattern SENTENCE_PATTERN =
            Pattern.compile(SENTENCE_REGEX);

    @Override
    public TextComponent parse(String input) {
        Paragraph paragraph = new Paragraph();

        if (input == null || input.isBlank()) {
            return paragraph;
        }

        String[] sentences = SENTENCE_PATTERN.split(input.strip());

        for (String sentenceText : sentences) {
            String trimmed = sentenceText.strip()
                    .replaceAll("\\s+", " ");

            if (!trimmed.isEmpty()) {
                TextComponent parsed = parseNext(trimmed);

                if (parsed instanceof Sentence sentence) {
                    paragraph.add(sentence);
                }
            }
        }

        return paragraph;
    }
}