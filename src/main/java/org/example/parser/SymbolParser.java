package org.example.parser;

import org.example.entity.Lexeme;
import org.example.entity.Symbol;
import org.example.entity.TextComponent;

public class SymbolParser extends AbstractTextParser {

    @Override
    public TextComponent parse(String input) {
        Lexeme lexeme = new Lexeme(input);

        if (input != null) {
            for (char character : input.toCharArray()) {
                lexeme.add(new Symbol(character));
            }
        }

        return lexeme;
    }
}