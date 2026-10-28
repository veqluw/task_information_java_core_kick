package org.example.entity;

import java.util.List;
import java.util.stream.Collectors;

public record Paragraph(List<Sentence> sentences) implements TextComponent {

    public Paragraph(List<Sentence> sentences) {
        if (sentences == null || sentences.isEmpty()) {
            throw new IllegalArgumentException("Paragraph must contain at least one sentence");
        }
        this.sentences = List.copyOf(sentences);
    }

    @Override
    public String asText() {
        return sentences.stream().map(Sentence::asText).collect(Collectors.joining(" "));
    }
}