package com.projectpacks.backend.services;

import java.io.*;

public class FileService {

    public static String UpdateFile(String place) {
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
        return "Saved successfully";
    }
    public static String[] ReadFile() {
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader("./src/main/resources/bookmarks.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
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
        String output = content.toString().split("\n")[0];
        String[] res = output.replaceAll("[\\[\\]]", "").trim().split(",");
        for (int i = 0; i < res.length; i++) {
            res[i] = res[i].trim();
        }
        return res;
    }
}
