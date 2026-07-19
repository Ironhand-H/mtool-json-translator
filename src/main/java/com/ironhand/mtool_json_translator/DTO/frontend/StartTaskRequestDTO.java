package com.ironhand.mtool_json_translator.DTO.frontend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartTaskRequestDTO {
    private String modelName;
    private String url;

    public StartTaskRequestDTO(String modelName, String url){
        this.modelName = modelName;
        this.url = url;
    }
}
