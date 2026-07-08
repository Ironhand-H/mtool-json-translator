package com.ironhand.mtool_json_translator.client;


import com.ironhand.mtool_json_translator.DTO.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

public class LMStudioClient {
    private RestClient restClient;

    public LMStudioClient(RestClient restClient){
        this.restClient = restClient;
    }

    // Not recommended for use, as it has not enough parameters to do better translation
    public ResponseResultDTO response(String input, String model, String uri) {
        RestClient restClient = RestClient.create();
        ResponseRequestDTO obj = new ResponseRequestDTO(model, input);

        ResponseEntity<ResponseResultDTO> result = restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(obj)
                .retrieve()
                .toEntity(ResponseResultDTO.class);

        return result.getBody();
    }

    public CompletionResultDTO completion(String model, String uri, String developerMessage, String systemMessage, Double temperature) {
        List<MessageDTO> messages = new ArrayList<>();

        messages.add(new MessageDTO("developer", developerMessage));
        messages.add(new MessageDTO("user", systemMessage));
        CompletionRequestDTO obj = new CompletionRequestDTO(model, temperature, messages);

        ResponseEntity<CompletionResultDTO> result = restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(obj)
                .retrieve()
                .toEntity(CompletionResultDTO.class);

        return result.getBody();
    }
}
