package org.example.parser;

import org.example.entity.Text;

public interface TextParser {
    Text parse(String source);
}