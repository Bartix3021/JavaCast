package com.projectpacks.backend.parsingStrategy;

import com.projectpacks.backend.objectStructure.WeatherData;

public interface ParsingStrategy {
    WeatherData[] parse(String response);
}
