package com.ironhand.mtool_json_translator;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ironhand.mtool_json_translator.service.*;


@SpringBootApplication
public class MtoolJsonTranslatorApplication {

	public static void main(String[] args)  {
//        FileFactory factory = new FileFactory("D:\\\\ManualTransFile.json");
//        factory.splitFile();

//        TranslatePipeline pipeline = new TranslatePipeline();
//        pipeline.MToolTranslate("qwen/qwen3-14b", "http://127.0.0.1:1234/v1/chat/completions", "D:\\ManualTransFile.json");

//        factory.forgeFile();

//        SpringApplication.run(MtoolJsonTranslatorApplication.class, args);
		SpringApplication app = new SpringApplication(MtoolJsonTranslatorApplication.class);
		app.setHeadless(false);
		app.run(args);
	}


}
