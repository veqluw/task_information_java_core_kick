package org.example.util;

import org.example.entity.Text;

public final class TextStatisticsService {
    public TextStatistics count(Text text) {
        String source = text.asText();
        long letters = source.codePoints().filter(Character::isLetter).count();
        long characters = source.codePointCount(0, source.length());
        return new TextStatistics(letters, characters);
    }
}
