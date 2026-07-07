package com.ironhand.mtool_json_translator.service;

import ch.qos.logback.core.testUtil.StringListAppender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.*;

public class TextFactory {
    public static LinkedHashMap<String, String> MToolJSONExtractor(JsonNode jsonNode) throws JsonProcessingException {
        if (jsonNode == null){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.treeToValue(jsonNode,
                new TypeReference<LinkedHashMap<String, String>>() {});
    }

    public static String mapToPrompt(LinkedHashMap<String, String> map) throws JsonProcessingException {
        nullCheck(map);

        List<String> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.add(entry.getValue());
        }

        return objectMapper.writeValueAsString(result);
    }

    public static String mapToPromptSafe(LinkedHashMap<String, String> map) throws JsonProcessingException {
        nullCheck(map);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        Integer index = 0;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            arrayNode.add(objectMapper.createObjectNode()
                    .put("id", index)
                    .put("text", entry.getValue()));
            index++;
        }

        return objectMapper.writeValueAsString(arrayNode);
    }

    public static List<String> mapToPromptStrict(LinkedHashMap<String, String> map) {
        nullCheck(map);

        List<String> strings = new ArrayList<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            strings.add(entry.getValue());
        }

        return strings;
    }



    public static LinkedHashMap<String, String> extractResult(String resultBody, LinkedHashMap<String, String> map) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        int i  = 0;

        List<String> translated = objectMapper.readValue(resultBody, new TypeReference<List<String>>(){});
        LinkedHashMap<String, String> result = new LinkedHashMap<>();

        if (translated.size() != map.size())
            throw new RuntimeException("Input value size not equals result value size");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.put(entry.getKey(), translated.get(i));
            i++;
        }

        return result;
    }

    public static LinkedHashMap<String, String> extractResultSafe(String resultBody, LinkedHashMap<String, String> map) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        int i  = 0;

        JsonNode translated = objectMapper.readTree(resultBody);
        LinkedHashMap<String, String> result = new LinkedHashMap<>();

        if (translated.size() != map.size())
            throw new RuntimeException("Input value size not equals result value size in safe mode.");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.put(entry.getKey(), translated.get(i).get("text").textValue());
            i++;
        }

        return result;
    }

    public static LinkedHashMap<String, String> extractResultStrict(List<String> resultBody, LinkedHashMap<String, String> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        int i  = 0;

        List<JsonNode> translated = new ArrayList<>();
        for (String s: resultBody){
            translated.add(objectMapper.createObjectNode().put("text", s));
        }

        LinkedHashMap<String, String> result = new LinkedHashMap<>();

        if (translated.size() != map.size())
            throw new RuntimeException("Input value size not equals result value size in safe mode.");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.put(entry.getKey(), translated.get(i).get("text").textValue());
            i++;
        }

        return result;
    }

    private static void nullCheck(LinkedHashMap<String, String> map){
        if (map.isEmpty()){
            throw new RuntimeException("Result is empty.");
        }
    }
}
