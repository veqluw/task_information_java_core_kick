package org.example.entity;

public class Sentence extends TextComposite {

    @Override
    protected String getChildSeparator() {
        return " ";
    }
}