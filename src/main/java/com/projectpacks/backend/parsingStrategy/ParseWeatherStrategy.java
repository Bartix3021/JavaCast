package com.projectpacks.backend.parsingStrategy;

import com.google.gson.Gson;
import com.projectpacks.backend.objectStructure.WeatherData;

public class ParseWeatherStrategy implements ParsingStrategy {

    @Override
    public WeatherData[] parse(String response) {
        Gson gson = new Gson();
        return new WeatherData[] {gson.fromJson(response, WeatherData.class)};
    }
}
