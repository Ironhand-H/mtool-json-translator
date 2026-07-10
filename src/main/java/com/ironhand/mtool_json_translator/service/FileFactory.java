package com.ironhand.mtool_json_translator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.ironhand.mtool_json_translator.service.PromptProvider.getDefaultPrompt;
import static com.ironhand.mtool_json_translator.service.TextFactory.MToolJSONExtractor;
import static com.ironhand.mtool_json_translator.service.TranslateBatch.separateText;

public class FileFactory {
    private final Path inputPath;
    private Path outputPath;

    public FileFactory(String path){
        this.inputPath = Paths.get(path);
    }

    public void generateTranslatedMTool(LinkedHashMap<String, String> translatedText, String originFilePath) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Iterator<Map.Entry<String, String>> ite = translatedText.entrySet().iterator();
        String fileText = objectMapper.writeValueAsString(translatedText);

        try{
            FileWriter fileWriter = new FileWriter(originFilePath + ".translated.json");
            fileWriter.write(fileText);
            fileWriter.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public void splitFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<LinkedHashMap<String, String>> separatedText = null;
            Integer index = 1;

            this.outputPath = this.inputPath.getParent().resolve("task");
            if (!Files.exists(outputPath))
                Files.createDirectory(outputPath);

            LinkedHashMap<String, String> originText = MToolJSONExtractor(JSONFileParser.mToolFileParser(inputPath));

            if (originText != null) {
                separatedText = separateText(originText, 5000, getDefaultPrompt().length());
                for (LinkedHashMap<String, String> oneFile: separatedText){
                    Path file = outputPath.resolve(index + ".json");

                    Files.writeString(file, objectMapper.writeValueAsString(oneFile));
                    index++;
                }

                this.saveProcess(2);
            }
        }
        catch(Exception e) {
            System.out.println("Split file error: " + e.getMessage());
        }

    }

    public void saveProcess(Integer batchProcess) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode config = null;

        try {
            Path path = this.outputPath.resolve("config.json");

            if (!Files.exists(path)){
                Files.createFile(path);
            }

            java.lang.String content = Files.readString(path);
            if (content.isEmpty()){
                config = objectMapper.createObjectNode();
            }else{
                config = (ObjectNode) objectMapper.readTree(content);
            }

            config.put("process", batchProcess);
            Files.writeString(path, config.toPrettyString());
        }
        catch(Exception e) {
            System.out.println("Split file error: " + e.getMessage());
        }

    }
}
