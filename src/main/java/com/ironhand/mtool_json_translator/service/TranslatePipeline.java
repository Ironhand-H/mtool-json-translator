package com.ironhand.mtool_json_translator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static com.ironhand.mtool_json_translator.service.TextFactory.*;
import static com.ironhand.mtool_json_translator.service.PromptProvider.*;
import static com.ironhand.mtool_json_translator.service.TranslateBatch.separateText;

public class TranslatePipeline {
    public void MToolTranslate(String model, String uri, String filePath, Integer batchLimit) throws JsonProcessingException {
        //System.out.println(TranslateService.response("你好", "deepseek-r1-distill-qwen-14b", "http://127.0.0.1:1234/v1/responses", 8192));
        //"D:\\ManualTransFile.json"

        StringBuilder prompt = null;
        StringBuilder result = null;
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> allText = MToolJSONExtractor(JSONFileParser.MToolFileParser(filePath));

        if (allText != null) {
            List<Map<String, String>> separatedText = separateText(allText, batchLimit, getDefaultPrompt().length());
            for (Map<String, String> batchText : separatedText){
                prompt = new StringBuilder(getDefaultPrompt());
                prompt.append(mapToPrompt(batchText));
                result = new StringBuilder(TranslateService.response(prompt.toString(), model, uri));
            }

            JsonNode translatedJSON = objectMapper.readTree(result.toString());

            result = new StringBuilder(translatedJSON.toPrettyString());

        }

//        Map<String, String> allText = null;
//        try {
//            allText = MToolJSONExtractor(jsonNode);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        if (allText != null) {
//            System.out.println(allText.values());
//        }
    }
}
