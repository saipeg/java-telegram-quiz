package com.sogomonian.spring.security.spring_security.service;

import com.sogomonian.spring.security.spring_security.entity.Currency;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

public class TestBot extends TelegramLongPollingBot {

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallBack(update.getCallbackQuery());
        }

        else if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }

//        if (update.hasMessage()) {
//            Message message = update.getMessage();
//            if (message.hasText()) {
//                execute(
//                        SendMessage.builder()
//                                .chatId(message.getChatId().toString())
//                                .text("You sent: \n\n" + message.getText())
//                                .build());
//            }
//
//        }

    }
    @SneakyThrows
    private void handleCallBack(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("Чем раньше ляжешь тем раньше встанешь")
                                .build());

    }

    @SneakyThrows
    private void handleMessage(Message message) {
        //handle command
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities()
                            .stream()
                            .filter(
                                    e -> "bot_command"
                                            .equals(e.getType())).findFirst();

            if (commandEntity.isPresent()) {
                String command = message
                        .getText()
                        .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command) {
                    case "/set_currency":
                        List<InlineKeyboardButton> buttons = new ArrayList<>();
                        for (Currency currency : Currency.values()) {
                            buttons.add(
                                    InlineKeyboardButton.builder()
                                            .text(currency.name())
                                            .callbackData("ORIGINAL:" + currency)
                                            .build());

                        }
                        execute(
                                SendMessage.builder()
                                        .text("Если тебя зовут *, то жми")
                                        .chatId(message.getChatId().toString())
                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(Collections.singleton(buttons)).build()
                                        )
                                        .build()
                        );


                }

            }

        }
    }

    @Override
    public String getBotUsername() {
        return "@omg_commerce_omg_bot";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @SneakyThrows
    public static void main(String[] args) {
        TestBot bot = new TestBot();
//        bot.execute(SendMessage.builder().chatId("279116").text("Hello From Java").build());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

}
