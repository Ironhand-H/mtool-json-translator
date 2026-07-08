package com.ironhand.mtool_json_translator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class JSONFileParser {
    public static JsonNode mToolFileParser(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path); //"D:/ManualTransFile.json"

        try {
            return objectMapper.readTree(file);
        }
        catch(Exception e) {
            System.out.println("File error: " + path + e.getMessage());
        }

        return null;
    }
}
