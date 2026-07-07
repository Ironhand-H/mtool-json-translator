package com.ironhand.mtool_json_translator;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ironhand.mtool_json_translator.service.*;

@SpringBootApplication
public class MtoolJsonTranslatorApplication {

	public static void main(String[] args) {
        TranslatePipeline pipeline = new TranslatePipeline();
        try {
            pipeline.MToolTranslate("qwen/qwen3-14b", "http://127.0.0.1:1234/v1/chat/completions", "D:\\ManualTransFile.json", 2500);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //SpringApplication.run(MtoolJsonTranslatorApplication.class, args);
	}

}
