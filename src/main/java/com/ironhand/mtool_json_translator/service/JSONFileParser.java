package com.ironhand.mtool_json_translator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONFileParser {
    public static JsonNode mToolFileParser(Path path) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String content = Files.readString(path);
            return objectMapper.readTree(content);
        }
        catch(Exception e) {
            System.out.println("File error: " + path + e.getMessage());
        }

        return null;
    }
}
