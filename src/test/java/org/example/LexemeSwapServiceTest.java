package org.example;

import org.example.parser.RegexTextParser;
import org.example.service.LexemeSwapService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LexemeSwapServiceTest {

    private final RegexTextParser parser = new RegexTextParser();

    private final LexemeSwapService operation =
            new LexemeSwapService();

    @Test
    void givenTwoLexemes_whenExecuted_thenSwapsThem() {
        // Given
        var text = parser.parse("Hello world.");

        // When
        var result = operation.execute(text);

        // Then
        assertThat(result.asText()).isEqualTo("world. Hello");
    }

    @Test
    void givenSingleLexeme_whenExecuted_thenSentenceRemainsUnchanged() {
        // Given
        var text = parser.parse("Hello!");

        // When
        var result = operation.execute(text);

        // Then
        assertThat(result.asText()).isEqualTo("Hello!");
    }

    @Test
    void givenMultipleSentences_whenExecuted_thenTransformsEachSentence() {
        // Given
        var text = parser.parse("One two three. Four five six!");

        // When
        var result = operation.execute(text);

        // Then
        assertThat(result.asText())
                .isEqualTo("three. two One six! five Four");
    }

    @Test
    void givenText_whenExecuted_thenOriginalTextRemainsUnchanged() {
        // Given
        var text = parser.parse("One two three.");

        // When
        operation.execute(text);

        // Then
        assertThat(text.asText()).isEqualTo("One two three.");
    }

    @Test
    void givenTwoParagraphs_whenExecuted_thenPreservesParagraphStructure() {
        // Given
        var text = parser.parse("""
                One two.

                Three four.
                """.strip());

        // When
        var result = operation.execute(text);

        // Then
        assertThat(result.paragraphs()).hasSize(2);
    }
}
