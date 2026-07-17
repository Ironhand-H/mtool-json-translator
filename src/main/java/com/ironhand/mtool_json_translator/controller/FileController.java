package com.ironhand.mtool_json_translator.controller;

import com.ironhand.mtool_json_translator.DTO.frontend.FileResponseDTO;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

@RestController
@RequestMapping("/api")
public class FileController {


    @PostMapping("/chooseFile")
    public FileResponseDTO chooseFile(@RequestBody String path) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JSON files", "json");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
            return new FileResponseDTO(chooser.getSelectedFile().getName());
        }else{
            return new FileResponseDTO("null choice");
        }


    }
}
