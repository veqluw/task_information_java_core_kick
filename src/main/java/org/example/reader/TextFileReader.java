package org.example.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exception.TextProcessingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextFileReader {

    private static final Logger LOGGER =
            LogManager.getLogger(TextFileReader.class);

    public String read(String filePath) {
        try {
            String content = Files.readString(Path.of(filePath));
            LOGGER.info("Text successfully read from {}", filePath);
            return content;
        } catch (IOException | RuntimeException exception) {
            throw new TextProcessingException(
                    "Could not read text file: " + filePath,
                    exception
            );
        }
    }
}