package com.sogomonian.spring.security.spring_security.service;

import com.sogomonian.spring.security.spring_security.entity.Currency;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

public class TestBot extends TelegramLongPollingBot {
    private final CurrencyModeService currencyModeService = CurrencyModeService.getInstance();

    private static final Logger log = LogManager.getLogger();

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallBack(update.getCallbackQuery());
        } else if (update.hasMessage()) {
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
                        .text("Внимание! Первый вопрос: Какой из этих вариантов ответов считается спорным принципом в ООП?")
                        .build());

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (Currency currency : Currency.values()) {
            buttons.add(
                    InlineKeyboardButton.builder()
                            .text(currency.name())
                            .callbackData("ORIGINAL:" + currency)
                            .build());

        }

        execute(
                EditMessageReplyMarkup.builder()
                        .chatId(message.getChatId().toString())
                        .messageId(message.getMessageId())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(Collections.singleton(buttons)).build())
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
                    case "/start":
                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                        Currency originalCurrency =
                                currencyModeService.getOriginalCurrency(message.getChatId());
                        Currency targetCurrency = currencyModeService.getTargetCurrency(message.getChatId());
                        for (Currency currency : Currency.values()) {
                            buttons.add(
                                    Arrays.asList(
                                            InlineKeyboardButton.builder()
                                                    .text(getCurrencyButton(originalCurrency, currency))
                                                    .callbackData("ORIGINAL:" + currency)
                                                    .build(),
                                            InlineKeyboardButton.builder()
                                                    .text(getCurrencyButton(targetCurrency, currency))
                                                    .callbackData("TARGET:" + currency)
                                                    .build()));
                        }
                        //buttons
                        execute(
                                SendMessage.builder()
                                        .text("Привет! Это тестовый бот для проверки знаний Java. Хочешь испытать удачу?")
                                        .chatId(message.getChatId().toString())
                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                        .build());
                        return;
                }

            }
        }
    }

    @SneakyThrows
    private String getCurrencyButton(Currency saved, Currency current) {
        return saved == current ? current + "✅" : current.name();

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
