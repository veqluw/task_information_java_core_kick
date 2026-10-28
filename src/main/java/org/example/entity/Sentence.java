package org.example.entity;

import java.util.List;
import java.util.stream.Collectors;

public record Sentence(List<Lexeme> lexemes) implements TextComponent {
    public Sentence(List<Lexeme> lexemes) {
        if (lexemes == null || lexemes.isEmpty()) {
            throw new IllegalArgumentException("Sentence must contain at least one lexeme");
        }
        this.lexemes = List.copyOf(lexemes);
    }

    @Override
    public String asText() {
        return lexemes.stream().map(Lexeme::asText).collect(Collectors.joining(" "));
    }
}
