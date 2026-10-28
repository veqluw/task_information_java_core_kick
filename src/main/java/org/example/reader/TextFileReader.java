package org.example.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exception.TextProcessingException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TextFileReader {
    private static final Logger LOGGER = LogManager.getLogger(TextFileReader.class);

    public String read(Path path) {
        if (path == null) {
            throw new TextProcessingException("Input path must not be null");
        }
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            LOGGER.info("Read {} characters from {}", content.length(), path);
            return content;
        } catch (IOException exception) {
            throw new TextProcessingException("Unable to read input file: " + path, exception);
        }
    }
}