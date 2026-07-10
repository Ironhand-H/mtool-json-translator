package com.ironhand.mtool_json_translator.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedHashMap;
import java.util.List;

import static com.ironhand.mtool_json_translator.service.TextFactory.*;
import static com.ironhand.mtool_json_translator.service.PromptProvider.*;

public class TranslatePipeline {
    public void MToolTranslate(String model, String uri, String inputPath) {
        //System.out.println(TranslateService.response("你好", "deepseek-r1-distill-qwen-14b", "http://127.0.0.1:1234/v1/responses", 8192));
        //"D:\\ManualTransFile.json"

        StringBuilder translatedBatchText = null;
        FileFactory fileFactory = new FileFactory(inputPath);
        int index = 0, process = fileFactory.readProcess();
        TranslateService service = new TranslateService();


        List<LinkedHashMap<String, String>> separatedText = fileFactory.readSplitedFiles();
        LinkedHashMap<String, String> translatedText = new LinkedHashMap<>();

        for (LinkedHashMap<String, String> batchText : separatedText) {
            if (index + 1 < process){
                index ++;
                continue;
            }

            System.out.printf("%-10s %-10s",
                    LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)),
                    ("Start batch: " + (index + 1) + "/" + separatedText.size() + "\n"));

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

            fileFactory.saveFileTranslated(translatedText, index+1);
            fileFactory.saveProcess(index+2);

            System.out.printf("%-10s %-10s", LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)), ("Batch completed: " + (index+1) + "/" + separatedText.size() + "\n"));
            index++;
        }
    }
}
