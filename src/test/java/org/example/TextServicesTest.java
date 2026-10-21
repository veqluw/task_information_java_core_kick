package org.example;

import org.example.entity.Text;
import org.example.parser.LexemeParser;
import org.example.parser.ParagraphParser;
import org.example.parser.SentenceParser;
import org.example.parser.SymbolParser;
import org.example.parser.TextParser;
import org.example.service.CommonWordService;
import org.example.service.LexemeSwapService;
import org.example.service.SentenceSortService;
import org.example.service.TextStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextServicesTest {

    private TextParser parser;
    private CommonWordService commonWordService;
    private SentenceSortService sentenceSortService;
    private LexemeSwapService lexemeSwapService;
    private TextStatisticsService textStatisticsService;

    @BeforeEach
    void setUp() {
        parser = new ParagraphParser();

        TextParser sentenceParser = new SentenceParser();
        TextParser lexemeParser = new LexemeParser();
        TextParser symbolParser = new SymbolParser();

        parser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(symbolParser);

        commonWordService = new CommonWordService();
        sentenceSortService = new SentenceSortService();
        lexemeSwapService = new LexemeSwapService();
        textStatisticsService = new TextStatisticsService();
    }

    private Text parse(String input) {
        return (Text) parser.parse(input);
    }

    // CommonWordService

    @Test
    void shouldFindWordAppearingInMostSentences() {
        // Given
        Text text = parse(
                "Java is useful. Java is popular. Testing is useful."
        );

        // When
        List<String> actual =
                commonWordService.findMostFrequentWords(text);

        // Then
        assertEquals(List.of("is"), actual);
    }

    @Test
    void shouldIgnoreCaseWhenCountingWords() {
        // Given
        Text text = parse("Java is useful. JAVA is popular. java is powerful.");

        // When
        List<String> actual =
                commonWordService.findMostFrequentWords(text);

        // Then
        assertEquals(List.of("java", "is"), actual);
    }

    @Test
    void shouldReturnEmptyListWhenTextIsEmpty() {
        // Given
        Text text = parse("");

        // When
        List<String> actual =
                commonWordService.findMostFrequentWords(text);

        // Then
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    // SentenceSortService

    @Test
    void shouldSortSentencesByLetterCountAscending() {
        // Given
        Text text = parse("Java is useful. Testing is useful. I code.");

        // When
        List<String> actual =
                sentenceSortService.sortByLetterCount(text, 'a');

        // Then
        assertEquals(
                List.of("Testing is useful.", "I code.", "Java is useful."),
                actual
        );
    }

    @Test
    void shouldIgnoreLetterCaseWhenSorting() {
        // Given
        Text text = parse("Apple. apple. BANANA.");

        // When
        List<String> actual =
                sentenceSortService.sortByLetterCount(text, 'a');

        // Then
        assertEquals(3, actual.size());
        assertEquals("Apple.", actual.get(0));
        assertEquals("apple.", actual.get(1));
        assertEquals("BANANA.", actual.get(2));
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoSentences() {
        // Given
        Text text = parse("");

        // When
        List<String> actual =
                sentenceSortService.sortByLetterCount(text, 'a');

        // Then
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    // LexemeSwapService

    @Test
    void shouldSwapFirstAndLastLexemes() {
        // Given
        Text text = parse("Java is very useful.");

        // When
        List<String> actual =
                lexemeSwapService.swapFirstAndLastLexemes(text);

        // Then
        assertEquals(List.of("useful. is very Java"), actual);
    }

    @Test
    void shouldKeepMiddleLexemesInTheirOriginalOrder() {
        // Given
        Text text = parse("One two three four.");

        // When
        List<String> actual =
                lexemeSwapService.swapFirstAndLastLexemes(text);

        // Then
        assertEquals(List.of("four. two three One"), actual);
    }

    @Test
    void shouldHandleSentenceContainingOneLexeme() {
        // Given
        Text text = parse("Hello.");

        // When
        List<String> actual =
                lexemeSwapService.swapFirstAndLastLexemes(text);

        // Then
        assertEquals(List.of("Hello."), actual);
    }


    // TextStatisticsService

    @Test
    void shouldCountLetters() {
        // Given
        Text text = parse("Hello world!");

        // When
        int actual = textStatisticsService.countLetters(text);

        // Then
        assertEquals(10, actual);
    }

    @Test
    void shouldReturnZeroLettersForEmptyText() {
        // Given
        Text text = parse("");

        // When
        int actual = textStatisticsService.countLetters(text);

        // Then
        assertEquals(0, actual);
    }

    @Test
    void shouldCountUnicodeLetters() {
        // Given
        Text text = parse("Привет мир!");

        // When
        int actual = textStatisticsService.countLetters(text);

        // Then
        assertEquals(9, actual);
    }
}