package com.ironhand.mtool_json_translator.controller;

import com.ironhand.mtool_json_translator.DTO.frontend.*;
import com.ironhand.mtool_json_translator.service.FileFactory;
import com.ironhand.mtool_json_translator.service.TranslatePipeline;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

@RestController
@RequestMapping("/api")
public class FileController {

    @PostMapping("/chooseFile")
    public APIResponseDTO<FileResponseDTO> chooseFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JSON files", "json");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return new APIResponseDTO<>(
                    true,
                    "Choose file success.",
                    new FileResponseDTO(
                            chooser.getSelectedFile().getAbsolutePath(),
                            chooser.getSelectedFile().getName()
                    ));
        }else{
            return new APIResponseDTO<>(
                    false,
                    "Choose file canceled.",
                    null);
        }
    }

    @PostMapping("/createProject")
    public APIResponseDTO<ProjectResponseDTO> createProject(@RequestBody CreateProjectRequestDTO request) {
        try{
            FileFactory factory = new FileFactory(request.getFileDir());
            factory.splitFile();

            return new APIResponseDTO<>(
                    true,
                    "Create project success.",
                    new ProjectResponseDTO(
                            factory.getConfigPath().toString(),
                            factory.readTotalBatch(),
                            factory.readCurrentBatch()));
        } catch (Exception e) {
            return new APIResponseDTO<>(
                    false,
                    "Create project failed: " + e.getMessage(),
                    null);
        }
    }

    @PostMapping("/readProject")
    public APIResponseDTO<ProjectResponseDTO> readProject() {
        try{
            FileFactory factory = new FileFactory();

            return new APIResponseDTO<>(
                    true,
                    "Read project success.",
                    new ProjectResponseDTO(
                            factory.getConfigPath().toString(),
                            factory.readTotalBatch(),
                            factory.readCurrentBatch()));
        } catch (Exception e) {
            return new APIResponseDTO<>(
                    false,
                    "Read project failed: " + e.getMessage(),
                    null);
        }
    }

    @PostMapping("/startTask")
    public APIResponseDTO<ProjectResponseDTO> startTask(@RequestBody StartTaskRequestDTO request) {
        try{
            TranslatePipeline pipeline = new TranslatePipeline();
            new Thread(() -> {
                pipeline.MToolTranslate(request.getModelName(), request.getUrl());  //"qwen/qwen3-14b", "http://127.0.0.1:1234/v1/chat/completions"
            }).start();

            return new APIResponseDTO<>(
                    true,
                    "Start or continue translate.",
                    null);
        } catch (Exception e) {
            return new APIResponseDTO<>(
                    false,
                    "Start or continue translate failed: " + e.getMessage(),
                    null);
        }
    }

    @PostMapping("/projectExist")
    public APIResponseDTO<ProjectResponseDTO> projectExist() {
        try{
            FileFactory factory = new FileFactory();
            if (factory.projectCreated()){
                return new APIResponseDTO<>(
                        true,
                        "A project is Created.",
                        new ProjectResponseDTO(
                                factory.getConfigPath().toString(),
                                factory.readTotalBatch(),
                                factory.readCurrentBatch()));
            }else {
                return new APIResponseDTO<>(
                        true,
                        "No created project found.",
                        null);
            }

        } catch (Exception e) {
            return new APIResponseDTO<>(
                    false,
                    "Read project status failed: " + e.getMessage(),
                    null);
        }
    }

}
