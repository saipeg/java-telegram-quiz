package com.sogomonian.spring.security.spring_security.service;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TestBot extends DefaultAbsSender {
    @Override
    public String getBotToken() {
        return "";
    }

    @SneakyThrows
    public static void main(String[] args) {
        TestBot testBot = new TestBot(new DefaultBotOptions());
        testBot.execute(SendMessage.builder().chatId("").text("Hello From Java").build());
    }
}
