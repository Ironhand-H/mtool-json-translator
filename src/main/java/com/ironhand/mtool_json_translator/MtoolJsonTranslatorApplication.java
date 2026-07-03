package com.ironhand.mtool_json_translator;

import com.ironhand.mtool_json_translator.service.Translate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ironhand.mtool_json_translator.client.LMStudioClient;

@SpringBootApplication
public class MtoolJsonTranslatorApplication {

	public static void main(String[] args) {
		System.out.println(Translate.response("你好", "deepseek-r1-distill-qwen-14b", "http://127.0.0.1:1234/v1/responses"));

		//SpringApplication.run(MtoolJsonTranslatorApplication.class, args);
	}

}
