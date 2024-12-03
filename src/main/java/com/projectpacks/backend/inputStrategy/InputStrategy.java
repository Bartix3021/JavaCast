package com.projectpacks.backend.inputStrategy;

import com.projectpacks.backend.objectStructure.WeatherData;

public interface InputStrategy {

    WeatherData[] inputData(String input);
}
