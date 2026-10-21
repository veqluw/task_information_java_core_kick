package org.example.entity;

public class Lexeme extends TextComposite {

    private final String value;

    public Lexeme(String value) {
        this.value = value;
    }

    @Override
    protected String getChildSeparator() {
        return "";
    }

    @Override
    public String getContent() {
        return value;
    }
}