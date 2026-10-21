package org.example.service;

import org.example.entity.Text;
import org.example.entity.TextComponent;

public class TextStatisticsService {

    public int countLetters(Text text) {
        int count = 0;

        for (TextComponent component : text.getChildren()) {
            count += countLetters(component.getContent());
        }

        return count;
    }

    public int countCharacters(Text text) {
        int count = 0;

        for (TextComponent component : text.getChildren()) {
            count += countNonWhitespaceCharacters(component.getContent());
        }

        return count;
    }

    private int countLetters(String content) {
        int count = 0;

        for (int index = 0; index < content.length(); index++) {
            if (Character.isLetter(content.charAt(index))) {
                count++;
            }
        }

        return count;
    }

    private int countNonWhitespaceCharacters(String content) {
        int count = 0;

        for (int index = 0; index < content.length(); index++) {
            if (!Character.isWhitespace(content.charAt(index))) {
                count++;
            }
        }

        return count;
    }
}
