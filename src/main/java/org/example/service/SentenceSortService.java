package org.example.service;

import org.example.entity.Sentence;
import org.example.entity.Text;

import java.util.Comparator;
import java.util.List;

public class SentenceSortService implements TextOperation<List<String>> {
    private final char targetLetter;

    public SentenceSortService(char targetLetter) {
        if (!Character.isLetter(targetLetter)) {
            throw new IllegalArgumentException("Target must be a letter");
        }
        this.targetLetter = Character.toLowerCase(targetLetter);
    }

    @Override
    public List<String> execute(Text text) {
        return text.paragraphs().stream()
                .flatMap(paragraph -> paragraph.sentences().stream())
                .sorted(Comparator.comparingLong(this::countTargetLetter))
                .map(Sentence::asText)
                .toList();
    }

    private long countTargetLetter(Sentence sentence) {
        return sentence.asText().codePoints()
                .filter(Character::isLetter)
                .filter(codePoint -> Character.toLowerCase(codePoint) == targetLetter)
                .count();
    }
}