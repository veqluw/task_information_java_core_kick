package org.example.parser;

import org.example.entity.TextComponent;

public abstract class AbstractTextParser implements TextParser {

    private TextParser nextParser;

    @Override
    public void setNextParser(TextParser nextParser) {
        this.nextParser = nextParser;
    }

    protected TextComponent parseNext(String input) {
        if (nextParser == null) {
            return null;
        }

        return nextParser.parse(input);
    }
}