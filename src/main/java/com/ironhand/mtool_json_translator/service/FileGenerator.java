package com.ironhand.mtool_json_translator.service;

import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {
    public static void generateTranslatedMToolFile(String result, String filePath){
        try {
            FileWriter myWriter = new FileWriter(filePath.replace(".json","_Translated.json"));
            myWriter.write(result);
            myWriter.close();  // must close manually
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
