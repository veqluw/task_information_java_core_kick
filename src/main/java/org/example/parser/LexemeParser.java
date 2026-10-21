package org.example.parser;

import org.example.entity.Lexeme;
import org.example.entity.Sentence;
import org.example.entity.TextComponent;

import java.util.regex.Pattern;

public class LexemeParser extends AbstractTextParser {

    private static final String LEXEME_REGEX = "\\s+";
    private static final Pattern LEXEME_PATTERN =
            Pattern.compile(LEXEME_REGEX);

    @Override
    public TextComponent parse(String input) {
        Sentence sentence = new Sentence();

        if (input == null || input.isBlank()) {
            return sentence;
        }

        String[] lexemes = LEXEME_PATTERN.split(input.strip());

        for (String lexemeText : lexemes) {
            if (!lexemeText.isEmpty()) {
                TextComponent parsed = parseNext(lexemeText);

                if (parsed instanceof Lexeme lexeme) {
                    sentence.add(lexeme);
                }
            }
        }

        return sentence;
    }
}