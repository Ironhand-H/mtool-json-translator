package com.ironhand.mtool_json_translator.service;

public class PromptProvider {

    public static String getDefaultPrompt() {
        return """
            You are a professional novel translator.

            Rules:
            - Translate the input text naturally into Simplified Chinese.
            - Preserve the original meaning.
            - Keep names and terminology consistent.
            - Do not explain.
            - Do not summarize.
            - Do not add emojis.
            - Do not modify words like "EV001".
            - Do not modify words with file extension, like "flower.png".
            - Do not modify pure numbers.
            - Return ONLY the translated text with format unchanged.
            
            
            """;
    }
}
