package org.example.entity;

import java.util.List;
import java.util.stream.Collectors;

public record Text(List<Paragraph> paragraphs) implements TextComponent {
    public Text(List<Paragraph> paragraphs) {
        if (paragraphs == null || paragraphs.isEmpty()) {
            throw new IllegalArgumentException("Text must contain at least one paragraph");
        }
        this.paragraphs = List.copyOf(paragraphs);
    }

    @Override
    public String asText() {
        return paragraphs.stream()
                .map(Paragraph::asText)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }
}
