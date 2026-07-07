package com.ironhand.mtool_json_translator.service;

import java.util.*;

public class TranslateBatch {
    public static List<LinkedHashMap<String, String>> separateText(LinkedHashMap<String, String> allText, Integer batchLimit, Integer promptSize){
        List<LinkedHashMap<String, String>> separatedText = new ArrayList<>();
        int count = promptSize;

        for (Map.Entry<String, String> pair : allText.entrySet()) {
            if (separatedText.isEmpty() || count >= batchLimit){
                separatedText.add(new LinkedHashMap<String, String>());
                count = promptSize;
            }

            separatedText.getLast().put(pair.getKey(), pair.getValue());
            count += pair.getValue().length();
        }

        return separatedText;
    }

    public static LinkedHashMap<String, String> assembleText(List<LinkedHashMap<String, String>> separatedText){
        LinkedHashMap<String, String> assembledText = new LinkedHashMap<>();

        separatedText.forEach(assembledText::putAll);

        return assembledText;
    }
}
