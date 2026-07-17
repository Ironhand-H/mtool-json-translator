package com.ironhand.mtool_json_translator.DTO.frontend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponseDTO {
    private String fileDir;

    public FileResponseDTO(String fileDir) {
        this.fileDir = fileDir;
    }
}
