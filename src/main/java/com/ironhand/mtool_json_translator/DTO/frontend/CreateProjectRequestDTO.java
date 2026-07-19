package com.ironhand.mtool_json_translator.DTO.frontend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectRequestDTO {
    private String fileDir;

    public CreateProjectRequestDTO(String fileDir){
        this.fileDir = fileDir;
    }
}
