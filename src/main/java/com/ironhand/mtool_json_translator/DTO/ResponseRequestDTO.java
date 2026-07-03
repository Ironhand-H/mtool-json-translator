package com.ironhand.mtool_json_translator.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseRequestDTO {
    private String model;
    private String input;

    public ResponseRequestDTO(String model, String input){
        this.model = model;
        this.input = input;
    }
}
