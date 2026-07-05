package com.ironhand.mtool_json_translator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class TextFactory {
    public static Map<String, String> MToolJSONExtractor(JsonNode jsonNode) throws JsonProcessingException {
        if (jsonNode == null){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.treeToValue(jsonNode,
                new TypeReference<Map<String, String>>() {});
    }

    public static String mapToPrompt(Map<String, String> map){
        StringBuilder result = new StringBuilder();
        if (!map.isEmpty()){
            result = new StringBuilder("[");

            for (Map.Entry<String, String> pair: map.entrySet()){
                result.append("\"").append(pair.getValue()).append("\",");
            }

            result.delete(result.length()-1, result.length());
            result.append("]");
        }

        return result.toString();
    }


}
