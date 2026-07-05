package com.ironhand.mtool_json_translator.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessagesDTO {
    private String role;
    private String content;
    private String refusal;

    public MessagesDTO(String role, String content){
        this.role = role;
        this.content = content;
        this.refusal = null;
    }

    public MessagesDTO(String role, String content, String refusal){
        this.role = role;
        this.content = content;
        this.refusal = refusal;
    }
}
