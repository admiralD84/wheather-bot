package com.company.service;

import com.company.models.WeatherData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CurrentWeatherServiceImpl implements ICurrentWeatherService {
    IFileService fileService;
    WeatherData currentWeather;

    public CurrentWeatherServiceImpl(IFileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void getWeather() {
        String inputJson = fileService.readFile();
        Type type = new TypeToken<WeatherData>() {
        }.getType();
        Gson gson = new Gson();
        currentWeather = gson.fromJson(inputJson, type);
    }

    @Override
    public String showWeather() {
        getWeather();
        StringBuilder tempString = new StringBuilder();
        String descriptionEmoji;
        String descriptionUz;
        String emojiTemp = "\uD83C\uDF21";

        switch (currentWeather.getWeather().get(0).getDescription()) {
            case "clear sky" -> {
                descriptionUz = "Ochiq havo, quyoshli";
                descriptionEmoji = "\u2600";
            }// quyoshli
            case "few clouds" -> {
                descriptionUz = "Qisman bulutli";
                descriptionEmoji = "\uD83C\uDF24";
            }// ozgina bulutli
            case "scattered clouds" -> {
                descriptionUz = "Bulutli";
                descriptionEmoji = "\uD83C\uDF25";           // bulutli
            }
            case "broken clouds" -> {
                descriptionEmoji = "\uD83C\uDF27";
                descriptionUz = "Qisman yomg’ir yog’ish ehtimoli mavjud";
            } //qora bulutli
            case "shower rain" -> {
                descriptionUz = "Kuchsiz yomg’ir";
                descriptionEmoji = "\uD83C\uDF26";                //yomg'ir sevalaydi

            }
            case "rain" -> {
                descriptionUz = "Yomg'irli";
                descriptionEmoji = "\uD83C\uDF27";                       // yomg'irli
            }
            case "thunderstorm" -> {
                descriptionUz = "Momaqaldiroq, kuchli yomg'ir";
                descriptionEmoji = "\uD83C\uDF29\uD83C\uDF2A";   // jala
            }
            case "snow" -> {
                descriptionUz = "Qor yog'adi";
                descriptionEmoji = "\uD83C\uDF28\u2744\u2744";           //qor yog'adi
            }
            case "mist" -> {
                descriptionUz = "Qalin tuman tushadi";
                descriptionEmoji = "\uD83C\uDF2B";                       // tuman
            }
            default -> {
                descriptionEmoji = "";
                descriptionUz = "";
            }
        }

        tempString.append("\n*").append(currentWeather.getName()).
                append("* hududida kutilayotgan ob-havo: \n\n").
                append("Ob-havo:   *").append(descriptionUz).append("*  ").
                append(descriptionEmoji).append(descriptionEmoji).append(descriptionEmoji).append("\n").
                append("Harorat:   *").append(currentWeather.getMain().getTemp()).
                append(" \u2103 ").append(emojiTemp).append("*\n").
                append("Tuyuladi:  *").append(currentWeather.getMain().getFeels_like()).
                append(" \u2103 ").append(emojiTemp).append("*\n");

        return tempString.toString();
    }

    @Override
    public String showDetailedInfo() {
        StringBuilder tempString = new StringBuilder(getTime());
        tempString.append(showWeather());
        tempString.append("\n*Bosim:* ").append(currentWeather.getMain().getPressure()).
                append(" hPa").append("\n*Namlik:* ").
                append(currentWeather.getMain().getHumidity()).append(" %").append("\n").
                append("*Quyosh chiqishi:* ").append(currentWeather.getSys().getSunrise()).append("  \uD83C\uDF1E\n").
                append("*Quyosh botishi:* ").append(currentWeather.getSys().getSunset()).append("  \uD83C\uDF12\n").
                append("*Shamol tezligi:* ").append(currentWeather.getWind().getSpeed()).append(" m/s\n").
                append("*Shamol yo'nalishi: * ").append(currentWeather.getWind().getDeg()).append(" \u00B0\n").
                append("*Bulutli qatlam: * ").append(currentWeather.getClouds().getAll()).append(" %\n").
                append("*Ko'rish darajasi: * ").append(currentWeather.getVisibility() / 1000).append(" km");

        return tempString.toString();
    }


    private String getTime() {
        StringBuilder timeString = new StringBuilder();
        long unixTime = currentWeather.getDt();
        java.util.Date date = new java.util.Date(unixTime * 1000L);
        java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("dd");
        java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM");
        java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy");
        String day = dayFormat.format(date);
        String month = monthFormat.format(date);
        String year = yearFormat.format(date);

        String monthString = null;
        switch (month) {
            case "01" -> monthString = "-yanvar";
            case "02" -> monthString = "-fevral";
            case "03" -> monthString = "-mart";
            case "04" -> monthString = "-aprel";
            case "05" -> monthString = "-may";
            case "06" -> monthString = "-iyun";
            case "07" -> monthString = "-iyul";
            case "08" -> monthString = "-avgust";
            case "09" -> monthString = "-sentabr";
            case "10" -> monthString = "-oktabr";
            case "11" -> monthString = "-noyabr";
            case "12" -> monthString = "-dekabr";
        }
        timeString.append("*Bugun ").
                append(year).append("-yil ").
                append(day).
                append(monthString).
                append("*\n");
        return timeString.toString();
    }

    @Override
    public String getLocation(double lat, double lon) {
        getWeather();
        return currentWeather.getName() + " " + currentWeather.getSys().getCountry();
    }

    @Override
    public String weatherMenu() {
        StringBuilder menuString = new StringBuilder();
        menuString.append("/location - send location\n");
        menuString.append("/weather - show current weather\n");
        menuString.append("/batafsil - show detailed information\n");

        return menuString.toString();
    }
}
