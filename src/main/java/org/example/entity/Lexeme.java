package org.example.entity;

public record Lexeme(String value) implements TextComponent {
    public Lexeme {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Lexeme must not be blank");
        }
    }

    @Override
    public String asText() {
        return value;
    }
}
