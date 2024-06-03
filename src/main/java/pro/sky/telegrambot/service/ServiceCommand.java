package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import static pro.sky.telegrambot.constants.Constants.*;

@Service
public class ServiceCommand {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot bot;

    // Сommands for the bot

    public void startCommand(Update update) {

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton infoBtn = new InlineKeyboardButton("Информация о приютах").callbackData("testcallback");
        keyboardMarkup.addRow(infoBtn);

        bot.execute(new SendMessage(getChatId(update.message()),
                update.message().chat().firstName() + START_TEXT ).replyMarkup(keyboardMarkup));
    }


    public void infoPr(Update update) {
        logger.info("Взван метод infoPr");

        bot.execute(new SendMessage(update.callbackQuery().message().chat().id(), INFO_TEXT));
        logger.info("КАЛЛБЭК ТЕКСТ "+ update.callbackQuery().data());

    }

    public void getUserData (Update update){
        logger.info("Взван метод getUserData");
        Long chatId = update.message().chat().id();

    }


    private Long getChatId(Message message) {
        return message.chat().id();
    }
}

