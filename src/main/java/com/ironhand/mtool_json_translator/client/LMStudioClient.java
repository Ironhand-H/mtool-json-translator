package com.ironhand.mtool_json_translator.client;


import com.ironhand.mtool_json_translator.request.responseRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class LMStudioClient {

    public String response(String input, String model, String uri) {
        RestClient restClient = RestClient.create();

        responseRequest obj = new responseRequest(model, input);

        ResponseEntity<String> result = restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(obj)
                .retrieve()
                .toEntity(String.class);

        return result.getBody();
    }
}
