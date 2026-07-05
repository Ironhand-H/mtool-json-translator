package com.ironhand.mtool_json_translator.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompletionResultDTO {
    private String id;

    private String object;

    private Integer created;

    private String model;

    private List<ChoiceDTO> choices;

    public CompletionResultDTO(String id, String object, Integer created, String model, List<ChoiceDTO> choices){
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
    }
}
