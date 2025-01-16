package com.projectpacks.javaproject;

import com.projectpacks.backend.forecast.method.CurrentForecastMethod;
import com.projectpacks.backend.forecast.method.FiveDayForecastMethod;
import com.projectpacks.backend.forecast.method.ForecastMethod;
import com.projectpacks.backend.forecast.method.TwoDayForecastMethod;
import com.projectpacks.backend.input.method.CityNameMethod;
import com.projectpacks.backend.input.method.IPMethod;
import com.projectpacks.backend.input.method.InputMethod;
import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.backend.observers.PeriodicalWeatherDataObserver;
import com.projectpacks.backend.services.FileService;
import com.projectpacks.backend.services.PeriodicalWeatherService;
import com.projectpacks.backend.services.WeatherService;
import com.projectpacks.backend.util.UnixToDate;

import java.io.File;

public class AppController {
    private static AppController instance;

    private final WeatherService weatherService;
    private final PeriodicalWeatherService periodicalService;

    private AppController() {
        this.weatherService = new WeatherService();
        this.periodicalService = new PeriodicalWeatherService(this.weatherService);
    }

    public void addPeriodicalObserver(PeriodicalWeatherDataObserver observer) {
        this.periodicalService.addObserver(observer);
    }

    public void removePeriodicalObserver(PeriodicalWeatherDataObserver observer) {
        this.periodicalService.removeObserver(observer);
    }

    public void startPeriodicalWeatherObservation(String place) {
        this.periodicalService.startUpdatingWeather(place);
    }

    public void stopPeriodicalWeatherObservation() {
        this.periodicalService.setEndFlag();
    }



    public WeatherData[] getWeatherForecast(String place) {
        return this.weatherService.getWeatherForecast(place);
    }

    public void setWeatherMethod(String method) {
        switch (method) {
            case "current":
                this.weatherService.setWeatherMethod(new CurrentForecastMethod());
                break;
            case "hourly":
                this.weatherService.setWeatherMethod(new TwoDayForecastMethod());
                break;
            case "forecast":
                this.weatherService.setWeatherMethod(new FiveDayForecastMethod());
                break;
            default:
                break;
        }
    }

    public void setInputMethod(String method) {
        switch (method) {
            case "city":
                this.weatherService.setInputMethod(new CityNameMethod());
                break;
            case "ip":
                this.weatherService.setInputMethod(new IPMethod());
                break;
            default:
                break;
        }
    }

    public WeatherData[] getDataByInput(String arg) {
        return this.weatherService.getDataByInput(arg);
    }


    public void UpdateFile(String content) {
        FileService.UpdateFile(content);
    }

    public String[] ReadFile() {
        return FileService.ReadFile();
    }

    public String convertUnixTimestampToDate(long dt, String format) {
        return UnixToDate.convertUnixTimestampToDate(dt, format);
    }

    public static AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }
}
