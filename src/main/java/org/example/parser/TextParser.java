package org.example.parser;

import org.example.entity.TextComponent;

public interface TextParser {

    TextComponent parse(String input);

    void setNextParser(TextParser nextParser);
}