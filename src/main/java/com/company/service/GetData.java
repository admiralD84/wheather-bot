package com.company.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GetData {
    IFileService fileService;

    public GetData(IFileService fileService) {
        this.fileService = fileService;
    }

    public void tryConnect(double lat, double lon) {
        //6200387544:AAH0COlvRNfRnBJ4ZnTlOyFzFmt_ta-x5Mg
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather" +
                    "?lat=" + lat +
                    "&lon=" + lon +
                    "&units=metric" +
                    "&appid=b18b0810c0b69f4ce19e8b2f8041446a");
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder inputText = new StringBuilder();
            String row;
            while((row = bufferedReader.readLine()) != null) {
                inputText.append(row);
            }
            this.writeToFile(inputText);
        } catch (Exception e) {
            System.out.println("URLga ulanishda xatolik");
            tryConnect(lat, lon);
        }
    }

    public void writeToFile(StringBuilder text) {
        fileService.writeFile(text.toString());
    }
}
