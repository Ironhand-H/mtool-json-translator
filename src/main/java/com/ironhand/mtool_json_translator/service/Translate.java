package com.ironhand.mtool_json_translator.service;

import com.ironhand.mtool_json_translator.DTO.ResponseResultDTO;
import com.ironhand.mtool_json_translator.client.LMStudioClient;

public class Translate {
    public static String response(String input, String model, String uri){
        LMStudioClient client =  new LMStudioClient();

        ResponseResultDTO result = client.response(input, model, uri); //"你好", "deepseek-r1-distill-qwen-14b", "http://127.0.0.1:1234/v1/responses"

        // 目前OpenAI-API返回的Output仅有两个类型reasoning和message，等未来有改动再更改
        if (result.getOutput().getFirst().getType().equals("message") )
            return result.getOutput().getFirst().getContent().getFirst().getText();
        else if (result.getOutput().getLast().getType().equals("message")) {
            return result.getOutput().getLast().getContent().getFirst().getText();
        } else
            return "";
    }
}
