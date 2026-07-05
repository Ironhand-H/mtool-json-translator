package com.ironhand.mtool_json_translator.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChoiceDTO {
    private String index;

    private String logprobs;

    private String finish_reason;

    private String model;

    public ChoiceDTO(String index, String logprobs, String finish_reason, String model){
        this.index = index;
        this.logprobs = logprobs;
        this.finish_reason = finish_reason;
        this.model = model;
    }
}
