package com.ironhand.mtool_json_translator.DTO.frontend;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class APIResponseDTO<T> {
    private boolean success;
    private String time;
    private String message;
    private T data;

    public APIResponseDTO(boolean success, String message ,T data){
        this.success = success;
        this.data = data;
        this.message = message;

        this.time = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
