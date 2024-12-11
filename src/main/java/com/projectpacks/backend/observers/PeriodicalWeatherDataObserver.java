package com.projectpacks.backend.observers;

import com.projectpacks.backend.models.WeatherData;

public interface PeriodicalWeatherDataObserver {
    void update(WeatherData[] newWeatherData);
}
