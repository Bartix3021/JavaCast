package com.projectpacks.backend.input.method;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpFetcher {

    public static String fetch() {
        String urlString = "https://api64.ipify.org?format=text"; // API endpoint

        try {
            // Create a URL object and open a connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String ip = in.readLine(); // The public IP address
            in.close();

            // Output the IP address
            System.out.println("Your public IP address is: " + ip);
            return ip;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
