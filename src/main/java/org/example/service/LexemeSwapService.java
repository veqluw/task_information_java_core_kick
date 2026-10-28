package org.example.service;

import org.example.entity.*;

import java.util.ArrayList;
import java.util.List;

public class LexemeSwapService implements TextOperation<Text> {
    @Override
    public Text execute(Text text) {
        List<Paragraph> transformedParagraphs = new ArrayList<>();
        for (Paragraph paragraph : text.paragraphs()) {
            List<Sentence> transformedSentences = new ArrayList<>();
            for (Sentence sentence : paragraph.sentences()) {
                List<Lexeme> lexemes = new ArrayList<>(sentence.lexemes());
                if (lexemes.size() > 1) {
                    int lastIndex = lexemes.size() - 1;
                    Lexeme first = lexemes.get(0);
                    lexemes.set(0, lexemes.get(lastIndex));
                    lexemes.set(lastIndex, first);
                }
                transformedSentences.add(new Sentence(lexemes));
            }
            transformedParagraphs.add(new Paragraph(transformedSentences));
        }
        return new Text(transformedParagraphs);
    }
}