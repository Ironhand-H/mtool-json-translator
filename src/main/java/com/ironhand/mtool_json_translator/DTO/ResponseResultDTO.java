package com.ironhand.mtool_json_translator.DTO;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseResultDTO {
    private String id;

    private String type;

    private String role;

    private String status;

    private List<OutputDTO> output;

    public ResponseResultDTO(String id, String type, String role, String status, List<OutputDTO> output){
        this.id = id;
        this.type = type;
        this.role = role;
        this.status = status;
        this.output = output;
    }
}
