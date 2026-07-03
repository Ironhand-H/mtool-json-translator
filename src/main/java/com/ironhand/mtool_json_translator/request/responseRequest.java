package com.ironhand.mtool_json_translator.request;

public class chatRequest {
    public String model;
    public String input;

    public chatRequest(String model, String input){
        this.model = model;
        this.input = input;
    }
}
