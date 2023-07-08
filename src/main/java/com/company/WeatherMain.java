package com.company;

import com.company.service.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;

public class WeatherMain {
    public static void main(String[] args) {
        IFileService fileService = new FileServiceImpl(new File("ob-havo.txt"));
        GetData dataFromURL = new GetData(fileService);
        dataFromURL.tryConnect(0, 0);
        ICurrentWeatherService currentWeather = new CurrentWeatherServiceImpl(fileService);

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyDemoTGBot(currentWeather, dataFromURL));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
