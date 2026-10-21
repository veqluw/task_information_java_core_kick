package org.example.service;

import org.example.entity.Text;
import org.example.entity.TextComponent;
import org.example.entity.Paragraph;
import org.example.entity.Sentence;
import org.example.entity.Lexeme;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class CommonWordService {

    private static final Pattern EDGE_PUNCTUATION =
            Pattern.compile("^[^\\p{L}\\p{N}']+|[^\\p{L}\\p{N}']+$");

    public List<String> findMostFrequentWords(Text text) {
        Map<String, Integer> sentenceFrequency = new LinkedHashMap<>();

        for (TextComponent paragraphComponent : text.getChildren()) {
            if (!(paragraphComponent instanceof Paragraph paragraph)) {
                continue;
            }

            for (TextComponent sentenceComponent : paragraph.getChildren()) {
                if (!(sentenceComponent instanceof Sentence sentence)) {
                    continue;
                }

                Set<String> uniqueWords = new LinkedHashSet<>();

                for (TextComponent lexemeComponent : sentence.getChildren()) {
                    if (!(lexemeComponent instanceof Lexeme lexeme)) {
                        continue;
                    }

                    String word = normalizeWord(lexeme.getContent());

                    if (!word.isEmpty()) {
                        uniqueWords.add(word);
                    }
                }

                for (String word : uniqueWords) {
                    sentenceFrequency.merge(word, 1, Integer::sum);
                }
            }
        }

        if (sentenceFrequency.isEmpty()) {
            return new ArrayList<>();
        }

        int maximumFrequency = sentenceFrequency.values()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        return sentenceFrequency.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maximumFrequency)
                .map(Map.Entry::getKey)
                .toList();
    }

    private String normalizeWord(String word) {
        return EDGE_PUNCTUATION.matcher(
                word.toLowerCase(Locale.ROOT)
        ).replaceAll("");
    }
}