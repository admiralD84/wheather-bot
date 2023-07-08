package com.company;

import com.company.service.GetData;
import com.company.service.ICurrentWeatherService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class MyDemoTGBot extends TelegramLongPollingBot {
    ICurrentWeatherService currentWeather;
    GetData myData;
    Map<Long, Location> locationMap;
    long chatId;
    double lon, lat;

    public MyDemoTGBot(ICurrentWeatherService currentWeather, GetData myData) {
        super("6200387544:AAH0COlvRNfRnBJ4ZnTlOyFzFmt_ta-x5Mg");
        this.currentWeather = currentWeather;
        this.myData = myData;
        locationMap = new HashMap<>();
    }

//    boolean isLocationHave = false;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackData = callbackQuery.getData();
            chatId = callbackQuery.getMessage().getChatId();
            Integer messageId = callbackQuery.getMessage().getMessageId();

            if (callbackData.equals("shahar_kirit")) {
                editMessageText("Shahar nomini kiritishingiz so'raladi", chatId, messageId);
            } else if (callbackData.equals("locatsiya_yubor")) {
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                KeyboardRow row = new KeyboardRow();
                keyboardMarkup.setResizeKeyboard(true);
                List<KeyboardRow> keyboard = new ArrayList<>();
                keyboardMarkup.setOneTimeKeyboard(true);
                keyboardMarkup.setSelective(true);
                KeyboardButton locationButton = new KeyboardButton();
                locationButton.setText("Joylashuvni yuborish \uD83D\uDCCD");
                locationButton.setRequestLocation(true);
                row.add(locationButton);
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Joylashuvni yuboring:");
                message.setReplyMarkup(keyboardMarkup);
                try {
                    execute(message);
                    //keyboardMarkup.setIsPersistent(false);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        if (update.hasMessage() && update.getMessage().hasLocation()) {
            Location location = update.getMessage().getLocation();
            chatId = update.getMessage().getChatId();
            locationMap.put(chatId, location);
            lat = locationMap.get(chatId).getLatitude();
            lon = locationMap.get(chatId).getLongitude();
            myData.tryConnect(lat, lon);
            sendKeyboard();
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            String tempText = update.getMessage().getText();

            switch (tempText) {
                case "/start" -> {
                    if (locationMap.size() > 0 && locationMap.containsKey(chatId))
                        sendKeyboard();
                    else {
                        sendInlineKeyboard(chatId);
                    }
                }
                case "Ob-havo \uD83C\uDF24" -> {
//                    if (isLocationHave)
                        sendMessage(currentWeather.showWeather(), chatId, false, null);
//                    else
//                        sendMessage("Shahar kiritilmadi, lokatsiyani yuboring", chatId, false, null);
                }
                case "Batafsil \uD83C\uDF24" -> {
//                    if (isLocationHave)
                        sendMessage(currentWeather.showDetailedInfo(), chatId, false, null);
//                    else
//                        sendMessage("Shahar kiritilmadi, lokatsiyani yuboring", chatId, false, null);
                }
                default ->
                    sendMessage("Noto'g'ri buyruq kiritildi", chatId, false, null);
                }
            }
        }


    @Override
    public String getBotUsername() {
        return "actual_weather_uz_bot";
    }

    private void sendMessage(
            String text,
            Long chatId,
            boolean reply,
            InlineKeyboardMarkup keyboardMarkup) {
        SendMessage message = new SendMessage();
        message.setParseMode("Markdown");
        message.setChatId(chatId);
        message.setText(text);
        if (reply)
            message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editMessageText(String newText, Long chatId, Integer messageId) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.setText(newText);

        try {
            execute((BotApiMethod<?>) editMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendInlineKeyboard(Long chatId) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("\uD83C\uDF07 Shahar kiritish");
        button1.setCallbackData("shahar_kirit");
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("\uD83D\uDCCD Joylashuvni yuborish");
        button2.setCallbackData("locatsiya_yubor");
        row1.add(button1);
        row1.add(button2);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        keyboardMarkup.setKeyboard(rows);

        String text = "O'z joylashuvingizni yuboring yoki shahar nomini kiriting";
        sendMessage(text, chatId, true, keyboardMarkup);
    }

    private void sendKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        keyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardButton wheatherButton = new KeyboardButton();
        wheatherButton.setText("Ob-havo \uD83C\uDF24");
        KeyboardButton moreInfoButton = new KeyboardButton();
        moreInfoButton.setText("Batafsil \uD83C\uDF24");
        row.add(wheatherButton);
        row.add(moreInfoButton);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        SendMessage message = new SendMessage();
        message.setParseMode("Markdown");
        message.setChatId(chatId);
        message.setText("*Joylashuv:* " + currentWeather.getLocation(lat, lon));
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
            keyboardMarkup.setIsPersistent(false);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}