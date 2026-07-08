package com.ironhand.mtool_json_translator.service;

import com.ironhand.mtool_json_translator.DTO.CompletionResultDTO;
import com.ironhand.mtool_json_translator.DTO.ResponseResultDTO;
import com.ironhand.mtool_json_translator.client.LMStudioClient;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.ironhand.mtool_json_translator.service.PromptProvider.getSimplePromptStrict;

public class TranslateService {
    RestClient restClient;

    public TranslateService(){
        this.restClient = initialize();
    }

    public RestClient initialize(){
        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(5000);      // 连接超时
        factory.setReadTimeout(300000);       // 读取超时（5分钟）

        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }

//    public static String response(String input, String model, String uri){
//        LMStudioClient client =  new LMStudioClient();
//
//        ResponseResultDTO result = client.response(input, model, uri); //"你好", "deepseek-r1-distill-qwen-14b", "http://127.0.0.1:1234/v1/responses"
//
//        // 目前OpenAI-API返回的Output仅有两个类型reasoning和message，等未来有改动再更改
//        if (result.getOutput().getFirst().getType().equals("message") )
//            return result.getOutput().getFirst().getContent().getFirst().getText();
//        else if (result.getOutput().getLast().getType().equals("message")) {
//            return result.getOutput().getLast().getContent().getFirst().getText();
//        } else
//            return "";
//    }

    public String completion(String model, String uri, String developerMessage, String systemMessage, Double temperature){
        CompletionResultDTO result = null;

        System.out.printf("%-10s %-10s", LocalTime.now().toString(), ("Posting..."));
        try {
            result = this.post(this.restClient, model, uri, developerMessage, systemMessage, temperature);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.print("Complete\n");
        return result.getChoices().getFirst().getMessage().getContent();
    }

    public List<String> completionStrict(String model, String uri, String developerMessage, List<String> systemMessage, Double temperature){
        List<String> result = new ArrayList<>();

        System.out.printf("%-10s %-10s", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME), ("Posting(Strict)..."));
        try{
            for (String s: systemMessage){
                CompletionResultDTO response = this.post(this.restClient, model, uri, developerMessage, s, temperature);
                result.add(response.getChoices().getFirst().getMessage().getContent().strip());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.print("Complete\n");
        return result;
    }

    private CompletionResultDTO post(
            RestClient restClient,
            String model, String uri,
            String developerMessage, String systemMessage,
            Double temperature) throws InterruptedException {

        LMStudioClient client = new LMStudioClient(restClient);

        for (int retry = 0; retry < 3; retry++) {

            try {
                return client.completion(model, uri, developerMessage, systemMessage, temperature);
            } catch (RuntimeException e) {
                System.out.println("Retrying: "
                        + (retry + 1) + "\n" );
                e.printStackTrace();

                Thread.sleep(5000);
            }

        }

        try {
            return client.completion(model, uri, getSimplePromptStrict(), systemMessage, temperature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException("LM Studio unavailable.");
    }
}
