package com.ironhand.mtool_json_translator.service;

import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {
    public static void pathFinder(){

    }

    public static void writeFile(String input, String filePath){
        try {
            //FileWriter myWriter = new FileWriter(filePath.replace(".json","_Translated.json"));
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(input);
            myWriter.close();  // must close manually
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
