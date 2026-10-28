package org.example;


import org.example.parser.RegexTextParser;
import org.example.service.CommonWordService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CommonWordServiceTest {

    private final RegexTextParser parser = new RegexTextParser();

    private final CommonWordService operation =
            new CommonWordService();

    @Test
    void givenDifferentWordCases_whenExecuted_thenMatchesCaseInsensitively() {
        // Given
        var text = parser.parse(
                "Java is useful. java is popular. JAVA is portable.");

        // When
        var result = operation.execute(text);

        // Then
        assertThat(result.sentenceCount()).isEqualTo(3);
        assertThat(result.words()).contains("java");
    }

    @Test
    void givenSeveralWordsWithEqualMaximum_whenExecuted_thenReturnsAllTiedWords() {
        // Given
        var text = parser.parse("Red blue. Red green. Blue green.");

        // When
        var result = operation.execute(text);

        // Then
        assertThat(result.sentenceCount()).isEqualTo(2);
        assertThat(result.words())
                .containsExactlyInAnyOrder("red", "blue", "green");
    }

    @Test
    void givenPunctuationAroundWords_whenExecuted_thenMatchesWordsCorrectly() {
        // Given
        var text = parser.parse("'Hello,' world! Hello, again.");

        // When
        var result = operation.execute(text);

        // Then
        assertThat(result.sentenceCount()).isEqualTo(2);
        assertThat(result.words()).contains("hello");
    }

    @Test
    void givenRepeatedWordInOneSentence_whenExecuted_thenCountsSentenceOnlyOnce() {
        // Given
        var text = parser.parse("echo echo echo. echo again.");

        // When
        var result = operation.execute(text);

        // Then
        assertThat(result.sentenceCount()).isEqualTo(2);
        assertThat(result.words()).contains("echo");
    }
}

