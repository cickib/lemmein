package com.codecool.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class TextReader {

    private static final Logger logger = LoggerFactory.getLogger(TextReader.class);

    public String getTextFromFile(String fileName) {
        StringBuilder text = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(text::append);
        } catch (IOException e) {
            logger.error("{} occurred while reading from file: {}.", e.getCause(), e.getMessage());
        }
        return text.toString();
    }
}
