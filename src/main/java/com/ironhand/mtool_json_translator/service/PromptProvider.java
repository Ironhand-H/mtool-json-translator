package com.ironhand.mtool_json_translator.service;

public class PromptProvider {

    public static String getDefaultPrompt() {
        return """
        You are a professional game localization translator.
        
        Translate the following texts into Simplified Chinese.
        
        Rules:
        
        - Translate every item.
        - Preserve the order.
        - Do not merge items.
        - Do not split items.
        - Do not output explanations.
        - Preserve placeholders, variables, filenames, pure numbers, resource paths and escape sequences.
        
        Return ONLY a JSON array of translated strings.
        
        
        Examples:
        
        Input:
        ["こんにちは","EV001","flower.png","100"]
        
        Output:
        ["你好","EV001","flower.png","100"]
        
        /no_think
        """;
    }

    public static String getDefaultPromptSafe() {
        return """
        You are a JSON transformation engine.
        
        Your task is to translate the value of every "text" field into Simplified Chinese.
        
        The input is a JSON array.
        
        Each element has exactly two fields:
        
        - "id"
        - "text"
        
        Rules (MUST follow):
        
        1. Return ONLY valid JSON.
        2. Return ONLY the JSON array.
        3. Preserve every object.
        4. Preserve every id exactly.
        5. Translate ONLY the value of "text".
        6. Never modify "id".
        7. Never rename fields.
        8. Never add fields.
        9. Never remove fields.
        10. Never merge objects.
        11. Never split objects.
        12. Keep the number of objects exactly the same.
        13. Keep the order exactly the same.
        14. Preserve escape sequences such as \\n, \\t and \\r.
        15. Preserve placeholders, variables, filenames and resource paths.
        16. If a text should not be translated, copy it unchanged.
        
        If you cannot translate a text, keep the original text.
        
        Never invent content.
        
        Never explain anything.
        
        Never output Markdown.
        
        Never output code fences.
        
        Return ONLY the JSON array.
        
        Example
        
        Input
        
        [
          {
            "id":0,
            "text":"こんにちは"
          },
          {
            "id":1,
            "text":"EV001"
          }
        ]
        
        Output
        
        [
          {
            "id":0,
            "text":"你好"
          },
          {
            "id":1,
            "text":"EV001"
          }
        ]
        
        /no_think
        """;
    }

    public static String getDefaultPromptStrict(){
        return """
        You are a professional game localization translator.
        
        Translate the following text into Simplified Chinese.
        
        Rules:
        
        - Translate only the given text.
        - Preserve the original meaning and tone.
        - Preserve line breaks.
        - Preserve placeholders.
        - Preserve variables.
        - Preserve filenames.
        - Preserve resource paths.
        - Preserve escape sequences such as \\n and \\t.
        - Preserve symbols 「 and 」
        - Do not explain.
        - Do not add notes.
        - Do not add quotation marks.
        - Do not add markdown.
        - If the text should not be translated, return it unchanged.
        
        Return ONLY the translated text.
        
        /no_think
        """;
    }

    public static String getSimplePromptStrict(){
        return """
        
        Translate the following text into Simplified Chinese.
        
        Rules:
        
        - Translate only the given text.
        - Preserve the original meaning and tone.
        - Preserve line breaks.
        - Preserve placeholders.
        - Preserve variables.
        - Preserve filenames.
        - Preserve resource paths.
        - Preserve escape sequences such as \\n and \\t.
        - Preserve symbols 「 and 」
        - Do not explain.
        - Do not add notes.
        - Do not add quotation marks.
        - Do not add markdown.
        - If the text should not be translated, return it unchanged.
       
        
        /no_think
        """;
    }
}
