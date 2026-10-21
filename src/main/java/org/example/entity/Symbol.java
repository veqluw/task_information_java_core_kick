package org.example.entity;

public class Symbol implements TextComponent {

    private final char value;

    public Symbol(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String getContent() {
        return String.valueOf(value);
    }
}