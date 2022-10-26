package com.catalina.webspringbootshop.telegram;



import com.catalina.webspringbootshop.entity.ServerUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.servlet.http.HttpServletRequest;

@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;


    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = new SendMessage();

            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText("Hi");

            System.out.println("update.getMessage().getChatId() = " + update.getMessage().getChatId());
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    static String chatId = "5016346952";

    public void sendMessage(ServerUser user, HttpServletRequest request) {
        try {
            SendMessage sendMessage = new SendMessage();


            sendMessage.setChatId(chatId);
            sendMessage.setText("Ip Address  \uD83D\uDCCD: " + user.getIpAddress() + "\n\n " + "When \uD83D\uDD66: "+user.getWhen()+"\n\n Where : "+user.getWhere());

            System.out.println("update.getMessage().getChatId() = ");
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}