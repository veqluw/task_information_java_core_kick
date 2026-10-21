package org.example;

import org.example.entity.Text;
import org.example.parser.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextParserTest {

    private TextParser parser;

    @BeforeEach
    void setUp() {
        parser = new ParagraphParser();

        TextParser sentenceParser = new SentenceParser();
        TextParser lexemeParser = new LexemeParser();
        TextParser symbolParser = new SymbolParser();

        parser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(symbolParser);
    }

    @Test
    void shouldParseSingleSentence() {
        // Given
        String input = "Hello world.";

        // When
        Text actual = (Text) parser.parse(input);

        // Then
        assertEquals("Hello world.", actual.getContent());
    }

    @Test
    void shouldNormalizeMultipleSpaces() {
        // Given
        String input = "Hello     world.";

        // When
        Text actual = (Text) parser.parse(input);

        // Then
        assertEquals("Hello world.", actual.getContent());
    }

    @Test
    void shouldSeparateParagraphs() {
        // Given
        String input = "First paragraph.\n\nSecond paragraph.";

        // When
        Text actual = (Text) parser.parse(input);

        // Then
        String expected = "First paragraph."
                + System.lineSeparator()
                + System.lineSeparator()
                + "Second paragraph.";

        assertEquals(expected, actual.getContent());
    }

    @Test
    void shouldReturnEmptyTextForEmptyInput() {
        // Given
        String input = "";

        // When
        Text actual = (Text) parser.parse(input);

        // Then
        assertNotNull(actual);
        assertEquals("", actual.getContent());
    }

    @Test
    void shouldHandleLeadingAndTrailingWhitespace() {
        // Given
        String input = "   Hello world.   ";

        // When
        Text actual = (Text) parser.parse(input);

        // Then
        assertEquals("Hello world.", actual.getContent());
    }
}