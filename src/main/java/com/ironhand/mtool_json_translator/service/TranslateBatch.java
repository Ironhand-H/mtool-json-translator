package com.ironhand.mtool_json_translator.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslateBatch {
    public static List<Map<String, String>> separateText(Map<String, String> allText, Integer batchLimit, Integer promptSize){
        List<Map<String, String>> separatedText = new ArrayList<>();
        int count = promptSize;

        for (Map.Entry<String, String> pair : allText.entrySet()) {
            if (separatedText.isEmpty() || count >= batchLimit){
                separatedText.add(new HashMap<String, String>());
                count = promptSize;
            }

            separatedText.getLast().put(pair.getKey(), pair.getValue());
            count += pair.getValue().length();
        }

        return separatedText;
    }

    public static Map<String, String> assembleText(List<Map<String, String>> separatedText){
        Map<String, String> assembledText = new HashMap<>();

        separatedText.forEach(assembledText::putAll);

        return assembledText;
    }
}
