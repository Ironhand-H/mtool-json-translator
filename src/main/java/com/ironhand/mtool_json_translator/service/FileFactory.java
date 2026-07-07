package com.ironhand.mtool_json_translator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileFactory {
    public static void generateTranslatedMTool(LinkedHashMap<String, String> translatedText, String originFilePath) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Iterator<Map.Entry<String, String>> ite = translatedText.entrySet().iterator();
        String fileText = objectMapper.writeValueAsString(translatedText);
//        PrintWriter out
//                = new PrintWriter(new BufferedWriter(new FileWriter(originFilePath + ".translated.json")));
        try{
            FileWriter fileWriter = new FileWriter(originFilePath + ".translated.json");
            fileWriter.write(fileText);
            fileWriter.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
