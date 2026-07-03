package com.ironhand.mtool_json_translator.client;


import com.ironhand.mtool_json_translator.DTO.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class LMStudioClient {

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
}
