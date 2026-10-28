package org.example;

import org.example.exception.TextProcessingException;
import org.example.parser.RegexTextParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegexTextParserTest {

    private final RegexTextParser parser = new RegexTextParser();

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\t", " \n\t "})
    void givenBlankInput_whenParsed_thenThrowsCustomException(
            String source) {

        // Given / When / Then
        assertThatThrownBy(() -> parser.parse(source))
                .isInstanceOf(TextProcessingException.class);
    }

    @Test
    void givenNullInput_whenParsed_thenThrowsCustomException() {
        // Given
        String source = null;

        // When / Then
        assertThatThrownBy(() -> parser.parse(source))
                .isInstanceOf(TextProcessingException.class);
    }

    @Test
    void givenThreeParagraphs_whenParsed_thenPreservesParagraphStructure() {
        // Given
        String source = """
                First paragraph.

                Second paragraph!

                Third paragraph?
                """.strip();

        // When
        var result = parser.parse(source);

        // Then
        assertThat(result.paragraphs()).hasSize(3);
        assertThat(result.paragraphs().get(0).asText())
                .isEqualTo("First paragraph.");
        assertThat(result.paragraphs().get(1).asText())
                .isEqualTo("Second paragraph!");
        assertThat(result.paragraphs().get(2).asText())
                .isEqualTo("Third paragraph?");
    }

    @Test
    void givenMultipleSentenceTerminators_whenParsed_thenSeparatesSentences() {
        // Given
        String source = "First sentence. Second sentence! Third sentence?";

        // When
        var result = parser.parse(source);

        // Then
        assertThat(result.paragraphs().get(0).sentences())
                .hasSize(3);
        assertThat(result.asText()).isEqualTo(source);
    }

    @Test
    void givenUnicodeText_whenParsed_thenPreservesContent() {
        // Given
        String source = "Привет, мир! More-or-less ready.";

        // When
        var result = parser.parse(source);

        // Then
        assertThat(result.asText()).isEqualTo(source);
    }

    @Test
    void givenRepeatedWhitespace_whenParsed_thenNormalizesWhitespace() {
        // Given
        String source = "One\t\t two   three.";

        // When
        var result = parser.parse(source);

        // Then
        assertThat(result.asText()).isEqualTo("One two three.");
    }

    @Test
    void givenParsedText_whenCollectionsAreModified_thenThrowsException() {
        // Given
        var result = parser.parse("One sentence.");

        // When / Then
        assertThatThrownBy(() -> result.paragraphs().clear())
                .isInstanceOf(UnsupportedOperationException.class);

        assertThatThrownBy(() ->
                result.paragraphs().get(0).sentences().clear())
                .isInstanceOf(UnsupportedOperationException.class);

        assertThatThrownBy(() ->
                result.paragraphs().get(0)
                        .sentences().get(0)
                        .lexemes().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
