package com.projectpacks.backend.inputStrategy;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpToCity {
    public static Ip getCity(String ip) {
        String urlString = "https://ipinfo.io/" + ip + "/json";

        try {
            // Send the GET request
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the JSON response
            Gson gson = new Gson();
            return gson.fromJson(response.toString(), Ip.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

