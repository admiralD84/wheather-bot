package com.company.models;

import java.util.Locale;

public class Sys {
    private int type;
    private int id;
    private String country;
    private long sunrise;
    private long sunset;

    public Sys(int type, int id, String country, long sunrise, long sunset) {
        this.type = type;
        this.id = id;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSunrise() {
        java.util.Date sunriseTime = new java.util.Date(sunrise * 1000L);
        java.text.SimpleDateFormat sunriseFormat = new java.text.SimpleDateFormat("HH:mm");
        return sunriseFormat.format(sunriseTime);
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        java.util.Date sunsetTime = new java.util.Date(sunset * 1000L);
        java.text.SimpleDateFormat sunsetFormat = new java.text.SimpleDateFormat("HH:mm");
        return sunsetFormat.format(sunsetTime);
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    @Override
    public String toString() {
        return
//                "Sys{" +
//                "type=" + type +
//                ", id=" + id +
                ", \uD83C\uDDFA\uD83C\uDDFF " + country.toLowerCase(Locale.ROOT)+
                "\nQuyosh chiqish vaqti: " + sunrise+
                "\nQuyosh botish vaqti: "  + sunset;

    }
}
