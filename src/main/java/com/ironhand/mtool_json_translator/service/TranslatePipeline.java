package com.ironhand.mtool_json_translator.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;

import static com.ironhand.mtool_json_translator.service.FileFactory.generateTranslatedMTool;
import static com.ironhand.mtool_json_translator.service.TextFactory.*;
import static com.ironhand.mtool_json_translator.service.PromptProvider.*;
import static com.ironhand.mtool_json_translator.service.TranslateBatch.separateText;

public class TranslatePipeline {
    public void MToolTranslate(String model, String uri, String filePath, Integer batchLimit) throws JsonProcessingException {
        //System.out.println(TranslateService.response("你好", "deepseek-r1-distill-qwen-14b", "http://127.0.0.1:1234/v1/responses", 8192));
        //"D:\\ManualTransFile.json"

        StringBuilder translatedBatchText = null;
        LinkedHashMap<String, String> originText = MToolJSONExtractor(JSONFileParser.mToolFileParser(filePath));
        LinkedHashMap<String, String> translatedText = new LinkedHashMap<>();
        TranslateService service = new TranslateService();
        int i = 1;

        if (originText != null) {
            List<LinkedHashMap<String, String>> separatedText = separateText(originText, batchLimit, getDefaultPrompt().length());

            for (LinkedHashMap<String, String> batchText : separatedText) {
                System.out.printf("%-10s %-10s", LocalTime.now().toString(), ("Start batch: " + i + "/" + separatedText.size() + "\n"));

                try{
                    translatedBatchText = new StringBuilder(service.completion(model, uri, getDefaultPrompt(), mapToPrompt(batchText), 0.1));
                    translatedText.putAll(extractResult(translatedBatchText.toString(), batchText));
                } catch (Exception e) {
                    try{
                        translatedBatchText = new StringBuilder(service.completion(model, uri, getDefaultPromptSafe(), mapToPromptSafe(batchText), 0.1));
                        translatedText.putAll(extractResultSafe(translatedBatchText.toString(), batchText));
                    } catch (Exception ex) {
                        try{
                            translatedText.putAll(extractResultStrict(service.completionStrict(
                                            model, uri,
                                            getDefaultPromptStrict(),
                                            mapToPromptStrict(batchText),
                                            0.1), batchText));
                        }catch (Exception exc){
                            throw new RuntimeException(exc);
                        }
                    }
                }

                System.out.printf("%-10s %-10s", LocalTime.now(), ("Batch completed: " + i + "/" + separatedText.size() + "\n"));
                i++;
            }
        }


        try {
            generateTranslatedMTool(translatedText, filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
