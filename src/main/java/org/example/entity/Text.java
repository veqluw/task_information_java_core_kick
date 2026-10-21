package org.example.entity;

public class Text extends TextComposite {

    @Override
    protected String getChildSeparator() {
        return System.lineSeparator() + System.lineSeparator();
    }
}