package com.ironhand.mtool_json_translator.DTO.frontend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponseDTO {
    private String configDir;
    private Integer totalBatch;
    private Integer currentBatch;

    public ProjectResponseDTO(String configDir, Integer totalBatch, Integer currentBatch){
        this.configDir = configDir;
        this.totalBatch = totalBatch;
        this.currentBatch = currentBatch;
    }
}
