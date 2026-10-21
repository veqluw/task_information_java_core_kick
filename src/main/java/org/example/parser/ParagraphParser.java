package org.example.parser;

import org.example.entity.Paragraph;
import org.example.entity.Text;
import org.example.entity.TextComponent;

import java.util.regex.Pattern;

public class ParagraphParser extends AbstractTextParser {

    private static final String PARAGRAPH_REGEX = "(?:\\R\\s*){2,}";
    private static final Pattern PARAGRAPH_PATTERN =
            Pattern.compile(PARAGRAPH_REGEX);

    @Override
    public TextComponent parse(String input) {
        Text text = new Text();

        String normalized = input == null ? "" : input.strip();

        if (normalized.isEmpty()) {
            return text;
        }

        String[] paragraphs = PARAGRAPH_PATTERN.split(normalized);

        for (String paragraphText : paragraphs) {
            String trimmed = paragraphText.strip()
                    .replaceAll("\\s+", " ");

            if (!trimmed.isEmpty()) {
                TextComponent parsed = parseNext(trimmed);

                if (parsed instanceof Paragraph paragraph) {
                    text.add(paragraph);
                }
            }
        }

        return text;
    }
}