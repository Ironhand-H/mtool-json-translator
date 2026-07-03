package com.ironhand.mtool_json_translator.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OutputDTO {
    private String id;

    private String type;

    private String status;

    private List<ContentDTO> content;
}
