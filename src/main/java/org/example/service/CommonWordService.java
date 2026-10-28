package org.example.service;

import org.example.entity.Text;
import org.example.entity.Paragraph;
import org.example.entity.Sentence;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CommonWordService implements TextOperation<CommonWordService.Result> {
    @Override
    public Result execute(Text text) {
        Map<String, Integer> sentenceCounts = new LinkedHashMap<>();
        for (Paragraph paragraph : text.paragraphs()) {
            for (Sentence sentence : paragraph.sentences()) {
                sentence.lexemes().stream()
                        .map(lexeme -> lexeme.value().replaceAll("^[^\\p{L}\\p{M}]+|[^\\p{L}\\p{M}]+$", ""))
                        .filter(word -> !word.isBlank())
                        .map(word -> word.toLowerCase(Locale.ROOT))
                        .distinct()
                        .forEach(word -> sentenceCounts.merge(word, 1, Integer::sum));
            }
        }
        if (sentenceCounts.isEmpty()) {
            return new Result(0, List.of());
        }
        int max = sentenceCounts.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        List<String> words = new ArrayList<>();
        sentenceCounts.forEach((word, count) -> {
            if (count == max) {
                words.add(word);
            }
        });
        return new Result(max, List.copyOf(words));
    }

    public record Result(int sentenceCount, List<String> words) {
        public Result {
            words = List.copyOf(words);
        }
    }
}