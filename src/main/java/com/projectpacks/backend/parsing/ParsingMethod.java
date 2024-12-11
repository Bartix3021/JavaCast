package com.projectpacks.backend.parsing;

import com.projectpacks.backend.models.WeatherData;

public interface ParsingMethod {
    WeatherData[] parse(String response);
}
