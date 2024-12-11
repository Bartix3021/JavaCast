package com.projectpacks.backend.parsing;

import com.google.gson.Gson;
import com.projectpacks.backend.models.WeatherData;

public class ParseWeatherMethod implements ParsingMethod {

    @Override
    public WeatherData[] parse(String response) {
        Gson gson = new Gson();
        return new WeatherData[] {gson.fromJson(response, WeatherData.class)};
    }
}
