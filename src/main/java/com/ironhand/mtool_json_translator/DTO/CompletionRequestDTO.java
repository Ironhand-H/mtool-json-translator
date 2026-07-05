package com.ironhand.mtool_json_translator.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompletionRequestDTO {
    private String model;
    private Integer temperature;
    private List<MessagesDTO> messages;

    public CompletionRequestDTO(String model, Integer temperature, List<MessagesDTO> messages){
        this.model = model;
        this.temperature = temperature;
        this.messages = messages;
    }
}
