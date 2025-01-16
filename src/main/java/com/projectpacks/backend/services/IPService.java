package com.projectpacks.backend.services;

import com.google.gson.Gson;
import com.projectpacks.backend.models.IP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IPService {
    public static String fetch() {
        String urlString = "https://api64.ipify.org?format=text"; // API endpoint

        try {

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String ip = in.readLine();
            in.close();

            return ip;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IP getCity(String ip) {
        String urlString = "https://ipinfo.io/" + ip + "/json";

        try {

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            return gson.fromJson(response.toString(), IP.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
