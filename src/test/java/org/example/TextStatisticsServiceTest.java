package org.example;

import org.example.parser.RegexTextParser;
import org.example.util.TextStatisticsService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TextStatisticsServiceTest {

    private final TextStatisticsService service =
            new TextStatisticsService();

    private final RegexTextParser parser = new RegexTextParser();

    @Test
    void givenLettersOnly_whenCount_thenCountsLettersAndCharacters() {
        // Given
        var text = parser.parse("Hello");

        // When
        var result = service.count(text);

        // Then
        assertThat(result.letterCount()).isEqualTo(5);
        assertThat(result.characterCount()).isEqualTo(5);
    }

    @Test
    void givenPunctuationDigitsAndSpaces_whenCount_thenCountsCorrectly() {
        // Given
        var text = parser.parse("A1, b!");

        // When
        var result = service.count(text);

        // Then
        assertThat(result.letterCount()).isEqualTo(2);
        assertThat(result.characterCount()).isEqualTo(6);
    }

    @Test
    void givenCyrillicText_whenCount_thenCountsCyrillicLetters() {
        // Given
        var text = parser.parse("Ёжик!");

        // When
        var result = service.count(text);

        // Then
        assertThat(result.letterCount()).isEqualTo(4);
        assertThat(result.characterCount()).isEqualTo(5);
    }

    @Test
    void givenSupplementaryUnicodeCharacter_whenCount_thenCountsCodePoints() {
        // Given
        var text = parser.parse("A 😀.");

        // When
        var result = service.count(text);

        // Then
        assertThat(result.letterCount()).isEqualTo(1);
        assertThat(result.characterCount()).isEqualTo(4);
    }
}
