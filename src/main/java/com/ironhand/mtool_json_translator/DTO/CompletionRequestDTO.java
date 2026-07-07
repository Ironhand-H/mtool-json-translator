package com.ironhand.mtool_json_translator.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompletionRequestDTO {
    private String model;
    private Double temperature;
    private List<MessageDTO> messages;

    public CompletionRequestDTO(String model, Double temperature, List<MessageDTO> messages){
        this.model = model;
        this.temperature = temperature;
        this.messages = messages;
    }
}
