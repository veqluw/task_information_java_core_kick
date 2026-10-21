package org.example.service;

import org.example.entity.Paragraph;
import org.example.entity.Sentence;
import org.example.entity.Text;
import org.example.entity.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class LexemeSwapService {

    public List<String> swapFirstAndLastLexemes(Text text) {
        List<String> result = new ArrayList<>();

        for (TextComponent paragraphComponent : text.getChildren()) {
            if (paragraphComponent instanceof Paragraph paragraph) {
                for (TextComponent sentenceComponent
                        : paragraph.getChildren()) {
                    if (sentenceComponent instanceof Sentence sentence) {
                        result.add(swap(sentence.getContent()));
                    }
                }
            }
        }

        return result;
    }

    private String swap(String sentence) {
        String[] lexemes = sentence.split("\\s+");

        if (lexemes.length < 2) {
            return sentence;
        }

        String first = lexemes[0];
        lexemes[0] = lexemes[lexemes.length - 1];
        lexemes[lexemes.length - 1] = first;

        return String.join(" ", lexemes);
    }
}