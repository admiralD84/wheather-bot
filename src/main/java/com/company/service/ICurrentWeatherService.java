package com.company.service;

public interface ICurrentWeatherService {
    void getWeather();

    String showWeather();

    String showDetailedInfo();

    String getLocation(double lat, double lon);

    String weatherMenu();

}
