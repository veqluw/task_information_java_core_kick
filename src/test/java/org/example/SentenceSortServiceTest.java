package org.example;

import org.example.parser.RegexTextParser;
import org.example.service.SentenceSortService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SentenceSortServiceTest {

    private final RegexTextParser parser = new RegexTextParser();

    @Test
    void givenMixedCase_whenSorted_thenCountsTargetLetterCaseInsensitively() {
        // Given
        var text = parser.parse("AAAA. aB. Bee.");

        // When
        var result =
                new SentenceSortService('a').execute(text);

        // Then
        assertThat(result).containsExactly("Bee.", "aB.", "AAAA.");
    }

    @Test
    void givenEqualCounts_whenSorted_thenPreservesRelativeOrder() {
        // Given
        var text = parser.parse("Cat. Map. Bat.");

        // When
        var result =
                new SentenceSortService('a').execute(text);

        // Then
        assertThat(result).containsExactly("Cat.", "Map.", "Bat.");
    }

    @Test
    void givenNoTargetOccurrences_whenSorted_thenPreservesOriginalOrder() {
        // Given
        var text = parser.parse("Sky. Cloud. Rain.");

        // When
        var result =
                new SentenceSortService('z').execute(text);

        // Then
        assertThat(result).containsExactly("Sky.", "Cloud.", "Rain.");
    }

    @Test
    void givenNonLetterTarget_whenOperationCreated_thenThrowsException() {
        // Given
        char target = '7';

        // When / Then
        assertThatThrownBy(
                () -> new SentenceSortService(target))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenCyrillicTarget_whenSorted_thenCountsCyrillicLetters() {
        // Given
        var text = parser.parse("Мир. Мама. Дом.");

        // When
        var result =
                new SentenceSortService('м').execute(text);

        // Then
        assertThat(result).containsExactly("Мир.", "Дом.", "Мама.");
    }
}
