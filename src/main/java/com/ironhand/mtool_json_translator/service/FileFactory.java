package com.ironhand.mtool_json_translator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

import static com.ironhand.mtool_json_translator.service.PromptProvider.getDefaultPrompt;
import static com.ironhand.mtool_json_translator.service.TextFactory.MToolJSONExtractor;
import static com.ironhand.mtool_json_translator.service.TranslateBatch.separateText;

@Getter
public class FileFactory {
    private final Path inputPath;
    private final Path batchPath;
    private final ObjectMapper objectMapper;
    private final Path splitedFilePath;
    private final Path configPath;
    private final Path outputPath;

    private final Integer MAX_LENGTH_BATCH = 2000;

    public FileFactory(String path){
        this.inputPath = Paths.get(path);
        this.objectMapper = new ObjectMapper();

        this.batchPath = Paths.get(
                System.getProperty("user.home"),
                "Documents",
                "AI Localization Toolkit");
        this.splitedFilePath = this.batchPath.resolve("tasks");
        this.configPath = this.batchPath.resolve("config.json");
        this.outputPath = this.batchPath.resolve("result");
    }

    public FileFactory(){
        this.inputPath = null;
        this.objectMapper = new ObjectMapper();

        this.batchPath = Paths.get(
                System.getProperty("user.home"),
                "Documents",
                "AI Localization Toolkit");
        this.splitedFilePath = this.batchPath.resolve("tasks");
        this.configPath = this.batchPath.resolve("config.json");
        this.outputPath = this.batchPath.resolve("result");
    }


    public void generateTranslatedMTool(LinkedHashMap<String, String> translatedText, String originFilePath) throws JsonProcessingException {
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
            List<LinkedHashMap<String, String>> separatedText = null;
            Integer index = 1;

            if (!Files.exists(batchPath))
                Files.createDirectory(batchPath);
            if (!Files.exists(outputPath))
                Files.createDirectory(outputPath);

            Files.copy(inputPath, batchPath);
            LinkedHashMap<String, String> originText = MToolJSONExtractor(JSONFileParser.mToolFileParser(inputPath));

            if (originText != null) {
                separatedText = separateText(originText, MAX_LENGTH_BATCH, getDefaultPrompt().length());
                for (LinkedHashMap<String, String> oneFile: separatedText){
                    Path file = splitedFilePath.resolve(index + ".json");

                    Files.writeString(file, objectMapper.writeValueAsString(oneFile));
                    index++;
                }

                this.initializeConfig(separatedText.size());
                this.saveProcess(1);
            }
        }
        catch(Exception e) {
            System.out.println("Split file error: " + e.getMessage());
        }
    }

    public void forgeFile(){
        Path fileDir = this.batchPath.getParent().resolve(
                "translated_" +
                        LocalTime.now().getHour() + "_" +
                        LocalTime.now().getMinute() + ".json");
        try{
            Files.createFile(fileDir);
            LinkedHashMap<String, String> output = new LinkedHashMap<>();

            List<LinkedHashMap<String, String>> content = this.readSplitFiles(this.outputPath);

            for (LinkedHashMap<String, String> batch: content){
                output.putAll(batch);
            }


            Files.writeString(fileDir, objectMapper.writeValueAsString(output));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<LinkedHashMap<String, String>> readSplitFiles(){
        List<LinkedHashMap<String, String>> output = new ArrayList<LinkedHashMap<String, String>>();
        Integer index = 1, max = this.readTotalBatch();
        String content;

        try{
            for (; index <= max; index++){
                content = Files.readString(this.batchPath.resolve(index + ".json"));
                output.add(this.objectMapper.treeToValue(
                        objectMapper.readTree(content),
                        new TypeReference<LinkedHashMap<String, String>>() {}));
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    public List<LinkedHashMap<String, String>> readSplitFiles(Path path){
        List<LinkedHashMap<String, String>> output = new ArrayList<LinkedHashMap<String, String>>();
        Integer index = 1, max = this.readTotalBatch();
        String content;

        try{
            for (; index <= max; index++){
                content = Files.readString(path.resolve(index + ".json"));
                output.add(this.objectMapper.treeToValue(
                        objectMapper.readTree(content),
                        new TypeReference<LinkedHashMap<String, String>>() {}));
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    public void saveFileTranslated(LinkedHashMap<String, String> input, Integer index) {
        try {
            if (input != null) {
                Files.writeString(this.outputPath.resolve(index + ".json"), objectMapper.writeValueAsString(input));
            }
        }
        catch(Exception e) {
            System.out.println("Save file error: " + e.getMessage());
        }
    }

    public void saveProcess(Integer process) {
        ObjectNode config = null;

        try {
            Path path = this.batchPath.resolve("config.json");

            if (!Files.exists(path)){
                Files.createFile(path);
            }

            String content = Files.readString(path);
            if (content.isEmpty()){
                config = objectMapper.createObjectNode();
            }else{
                config = (ObjectNode) objectMapper.readTree(content);
            }

            config.put("process", process);
            Files.writeString(path, config.toPrettyString());
        }
        catch(Exception e) {
            System.out.println("Writing config error: " + e.getMessage());
        }
    }

    public Integer readCurrentBatch() {
        Integer process = 0;

        try {
            if (!Files.exists(configPath))
                throw new FileNotFoundException("Config file of project not found.");

            String content = Files.readString(configPath);
            if (content.isEmpty()){
                throw new Exception("Config file damaged.");
            }else{
                process = objectMapper.readTree(content).get("process").asInt();
            }
        }
        catch(Exception e) {
            System.out.println("Reading process error: " + e.getMessage());
        }

        return process;
    }

    public Integer readTotalBatch() {
        Integer process = 0;

        try {
            if (!Files.exists(configPath))
                throw new FileNotFoundException("Config file of project not found.");

            String content = Files.readString(configPath);
            if (content.isEmpty()){
                throw new Exception("Config file damaged.");
            }else{
                process = objectMapper.readTree(content).get("total_batch").asInt();
            }
        }
        catch(Exception e) {
            System.out.println("Reading process error: " + e.getMessage());
        }

        return process;
    }

    public void initializeConfig(Integer countBatches) {
        ObjectNode config = null;

        try {
            Path path = this.batchPath.resolve("config.json");

            if (!Files.exists(path)){
                Files.createFile(path);
            }

            String content = Files.readString(path);
            if (content.isEmpty()){
                config = objectMapper.createObjectNode();
            }else{
                config = (ObjectNode) objectMapper.readTree(content);
            }

            config.put("total_batch", countBatches);
            config.put("create_time", LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
            Files.writeString(path, config.toPrettyString());
        }
        catch(Exception e) {
            System.out.println("Writing config error: " + e.getMessage());
        }
    }
}
