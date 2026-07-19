package com.ironhand.mtool_json_translator.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedHashMap;
import java.util.List;

import static com.ironhand.mtool_json_translator.service.TextFactory.*;
import static com.ironhand.mtool_json_translator.service.PromptProvider.*;

public class TranslatePipeline {
    private final Double TEMPERATURE = 0.1;

    public void MToolTranslate(String model, String uri) {
        //System.out.println(TranslateService.response("你好", "deepseek-r1-distill-qwen-14b", "http://127.0.0.1:1234/v1/responses", 8192));
        //"D:\\ManualTransFile.json"

        StringBuilder translatedBatchText = null;
        FileFactory fileFactory = new FileFactory();
        int index = 0, process = fileFactory.readCurrentBatch();
        TranslateService service = new TranslateService();


        List<LinkedHashMap<String, String>> separatedText = fileFactory.readSplitFiles();
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
                translatedBatchText = new StringBuilder(service.completion(
                        model, uri,
                        getDefaultPrompt(), mapToPrompt(batchText),
                        this.TEMPERATURE));
                translatedText.putAll(extractResult(translatedBatchText.toString(), batchText));
            } catch (Exception e) {
                try{
                    translatedBatchText = new StringBuilder(service.completion(
                            model, uri,
                            getDefaultPromptSafe(), mapToPromptSafe(batchText),
                            this.TEMPERATURE));
                    translatedText.putAll(extractResultSafe(translatedBatchText.toString(), batchText));
                } catch (Exception ex) {
                    try{
                        translatedText.putAll(extractResultStrict(service.completionStrict(
                                        model, uri,
                                        getDefaultPromptStrict(),
                                        mapToPromptStrict(batchText),
                                        this.TEMPERATURE), batchText));
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
