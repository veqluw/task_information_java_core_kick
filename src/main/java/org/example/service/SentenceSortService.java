package org.example.service;

import org.example.entity.Paragraph;
import org.example.entity.Sentence;
import org.example.entity.Text;
import org.example.entity.TextComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SentenceSortService {

    public List<String> sortByLetterCount(Text text, char requestedLetter) {
        List<SentenceWithCount> sentences = new ArrayList<>();

        for (TextComponent paragraphComponent : text.getChildren()) {
            if (paragraphComponent instanceof Paragraph paragraph) {
                for (TextComponent sentenceComponent
                        : paragraph.getChildren()) {
                    if (sentenceComponent instanceof Sentence sentence) {
                        String content = sentence.getContent();
                        int count = countLetter(content, requestedLetter);

                        sentences.add(new SentenceWithCount(content, count));
                    }
                }
            }
        }

        sentences.sort(Comparator.comparingInt(SentenceWithCount::count));

        List<String> result = new ArrayList<>();

        for (SentenceWithCount item : sentences) {
            result.add(item.content());
        }

        return result;
    }

    private int countLetter(String text, char requestedLetter) {
        char target = Character.toLowerCase(requestedLetter);
        int count = 0;

        for (int index = 0; index < text.length(); index++) {
            if (Character.toLowerCase(text.charAt(index)) == target) {
                count++;
            }
        }

        return count;
    }

    private record SentenceWithCount(String content, int count) {
    }
}