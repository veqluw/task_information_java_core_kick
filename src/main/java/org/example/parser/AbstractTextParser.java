package org.example.parser;

public abstract class AbstractTextParser {
    private AbstractTextParser next;

    public AbstractTextParser linkWith(AbstractTextParser nextParser) {
        this.next = nextParser;
        return nextParser;
    }

    protected final AbstractTextParser next() {
        return next;
    }
}