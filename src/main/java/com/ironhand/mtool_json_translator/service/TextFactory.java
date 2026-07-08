package com.ironhand.mtool_json_translator.service;

import ch.qos.logback.core.testUtil.StringListAppender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.time.LocalTime;
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

        System.out.printf("%-10s %-10s", LocalTime.now().toString(), ("从Map到提示词转换（Fast）..."));
        List<String> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.add(entry.getValue());
        }

        System.out.print("转换成功\n");
        return objectMapper.writeValueAsString(result);
    }

    public static String mapToPromptSafe(LinkedHashMap<String, String> map) throws JsonProcessingException {
        nullCheck(map);

        System.out.printf("%-10s %-10s", LocalTime.now().toString(), ("从Map到提示词转换（Safe）..."));
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        Integer index = 0;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            arrayNode.add(objectMapper.createObjectNode()
                    .put("id", index)
                    .put("text", entry.getValue()));
            index++;
        }

        System.out.print("转换成功\n");
        return objectMapper.writeValueAsString(arrayNode);
    }

    public static List<String> mapToPromptStrict(LinkedHashMap<String, String> map) {
        nullCheck(map);

        System.out.printf("%-10s %-10s", LocalTime.now().toString(), ("从Map到提示词转换（Strict）..."));
        List<String> strings = new ArrayList<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            strings.add(entry.getValue());
        }

        System.out.print("转换成功\n");
        return strings;
    }



    public static LinkedHashMap<String, String> extractResult(String resultBody, LinkedHashMap<String, String> map) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        int i  = 0;

        System.out.printf("%-10s %-10s", LocalTime.now().toString(), ("提取结果..."));
        List<String> translated = objectMapper.readValue(resultBody, new TypeReference<List<String>>(){});
        LinkedHashMap<String, String> result = new LinkedHashMap<>();

        System.out.print("转换成功，从List到Map\n");

        if (translated.size() != map.size())
            throw new RuntimeException("Input value size not equals result value size");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.put(entry.getKey(), translated.get(i));
            i++;
        }

        System.out.print("Complete\n");
        return result;
    }

    public static LinkedHashMap<String, String> extractResultSafe(String resultBody, LinkedHashMap<String, String> map) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        int i  = 0;

        System.out.printf("%-10s %-10s", LocalTime.now().toString(), ("提取结果（Safe）..."));
        JsonNode translated = objectMapper.readTree(resultBody);
        LinkedHashMap<String, String> result = new LinkedHashMap<>();

        if (translated.size() != map.size())
            throw new RuntimeException("Input value size not equals result value size in safe mode.");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.put(entry.getKey(), translated.get(i).get("text").textValue());
            i++;
        }

        System.out.print("Complete\n");
        return result;
    }

    public static LinkedHashMap<String, String> extractResultStrict(List<String> resultBody, LinkedHashMap<String, String> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        int i  = 0;

        System.out.printf("%-10s %-10s", LocalTime.now().toString(), ("提取结果（Strict）..."));
        List<JsonNode> translated = new ArrayList<>();
        for (String s: resultBody){
            translated.add(objectMapper.createObjectNode().put("text", s));
        }

        System.out.print("转换成功，从ListJsonNode到Map...");

        LinkedHashMap<String, String> result = new LinkedHashMap<>();

        if (translated.size() != map.size())
            throw new RuntimeException("Input value size not equals result value size in safe mode.");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.put(entry.getKey(), translated.get(i).get("text").textValue());
            i++;
        }

        System.out.print("Complete\n");
        return result;
    }

    private static void nullCheck(LinkedHashMap<String, String> map){
        if (map.isEmpty()){
            throw new RuntimeException("Result is empty.");
        }
    }
}
