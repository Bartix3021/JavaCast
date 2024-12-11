package com.projectpacks.backend.services;

import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.backend.parsing.ParsingMethod;
import com.projectpacks.backend.parsing.ParseForecastMethod;
import com.projectpacks.backend.parsing.ParseWeatherMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherAPI {

    private static final String apiKey = "c1eadebb1cf3ba7f6ea302b77a19ca3f";
    private static final String baseURL = "https://api.openweathermap.org/data/2.5/";
    private ParsingMethod pStrategy;

    public OpenWeatherAPI() {
        pStrategy = new ParseWeatherMethod();
    }



    public WeatherData[] getWeeklyForecast(String place) {
        pStrategy = new ParseForecastMethod();
        place = place.replaceAll(" ", "+");
        String urlString = baseURL + "forecast?q=" + place + "&exclude=current,minutely,hourly,alerts&appid=" + apiKey + "&units=metric";
        String result = fetchFromApi(urlString);
        System.out.println(result);
        return pStrategy.parse(result);
    }

    public static String fetchFromApi(String urlString) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                in.close();
            } else {
                System.out.println(responseCode);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return result.toString();
    }

    public static String urlBuilder(String type, String place, String additional) {
        return baseURL + type + "?q=" + place + additional + "&appid=" + apiKey + "&units=metric";
    }

    public static String urlBuilder(String type, String place) {
        return urlBuilder(type, place, "");
    }

}
