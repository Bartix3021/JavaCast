package com.projectpacks.backend.forecastStrategies;

import com.projectpacks.backend.objectStructure.WeatherData;
import com.projectpacks.backend.parsingStrategy.ParsingStrategy;
import com.projectpacks.backend.parsingStrategy.ParseForecastStrategy;
import com.projectpacks.backend.parsingStrategy.ParseWeatherStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherAPI {

    private static final String apiKey = "c1eadebb1cf3ba7f6ea302b77a19ca3f";
    private static final String baseURL = "https://api.openweathermap.org/data/2.5/";
    private ParsingStrategy pStrategy;

    public OpenWeatherAPI() {
        pStrategy = new ParseWeatherStrategy();
    }

    public WeatherData[] getCurrentWeather(String place) {
        pStrategy = new ParseWeatherStrategy();
        place = place.replaceAll(" ", "+");
        String urlString = baseURL + "weather?q=" + place + "&appid=" + apiKey + "&units=metric";
        String result = fetchFromApi(urlString);
        System.out.println(result);
        return pStrategy.parse(result);
    }

    public WeatherData[] getTwoDayForecast(String place) {
        pStrategy = new ParseForecastStrategy();
        place = place.replaceAll(" ", "+");
        String urlString = baseURL + "forecast?q=" + place + "&exclude=current,minutely,daily,alerts&appid=" + apiKey + "&units=metric";
        String result = fetchFromApi(urlString);
        System.out.println(result);
        return pStrategy.parse(result);
    }

    public WeatherData[] getWeeklyForecast(String place) {
        pStrategy = new ParseForecastStrategy();
        place = place.replaceAll(" ", "+");
        String urlString = baseURL + "forecast?q=" + place + "&exclude=current,minutely,hourly,alerts&appid=" + apiKey + "&units=metric";
        String result = fetchFromApi(urlString);
        System.out.println(result);
        return pStrategy.parse(result);
    }

    public String fetchFromApi(String urlString) {
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
}
