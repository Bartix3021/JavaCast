package com.projectpacks.backend.services;
import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.backend.observers.PeriodicalWeatherDataObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PeriodicalWeatherService {
    private List<PeriodicalWeatherDataObserver> observers = new ArrayList<>();
    private WeatherService apiService;
    private String place;
    private boolean endFlag = false;

    public PeriodicalWeatherService(WeatherService apiService) {
        this.apiService = apiService;
        endFlag = false;
    }

    public void addObserver(PeriodicalWeatherDataObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PeriodicalWeatherDataObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() throws IOException {
        WeatherData[] weatherData = apiService.getWeatherForecast(place);
        for (PeriodicalWeatherDataObserver observer : observers) {
            observer.update(weatherData);
        }
    }

    public void startUpdatingWeather(String place) {
        this.place = place;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (endFlag) {
                        timer.cancel();
                        return;
                    }
                    notifyObservers();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 5 * 60 * 1000);
    }

    public void setEndFlag() {
        endFlag = true;
    }
}
