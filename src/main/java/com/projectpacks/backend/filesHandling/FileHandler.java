package com.projectpacks.backend.filesHandling;

import java.io.*;
import java.util.Arrays;

public class FileHandler {

    public static String UpdateFile(String place) {
        BufferedWriter writer = null;
        try {
            // Open the file
            writer = new BufferedWriter(new FileWriter("./src/main/resources/bookmarks.txt"));
            // Write data to the file
            writer.write(place);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the writer
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
            // Open the file
            reader = new BufferedReader(new FileReader("./src/main/resources/bookmarks.txt"));
            String line;
            // Read each line
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the reader
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String output = content.toString().split("\n")[0];
        String[] res = output.substring(1, output.length() - 1).split(",");
        for (int i = 0; i < res.length; i++) {
            res[i] = res[i].trim();
        }
        return res;
    }
}
