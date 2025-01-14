package com.projectpacks.backend.services;

import java.io.*;

public class FileService {

    public static void UpdateFile(String place) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("./src/main/resources/bookmarks.txt"));
            writer.write(place);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String[] ReadFile() {
        BufferedReader reader = null;
        String content =  "";
        try {
            reader = new BufferedReader(new FileReader("./src/main/resources/bookmarks.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.split("\n");
    }
}
