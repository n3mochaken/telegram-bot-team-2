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

    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        try {
            bot.execute(sendMessage);
        } catch (Exception e) {
            logger.error("Error sending message", e);
        }
    }


    public void startCommand(Update update) {

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton infoBtn = new InlineKeyboardButton("Информация о приютах").callbackData(INFO);
        keyboardMarkup.addRow(infoBtn);

        bot.execute(new SendMessage(getChatId(update.message()),
                update.message().chat().firstName() + START_TEXT ).replyMarkup(keyboardMarkup));


    }


    public void infoPr(Update update) {
        bot.execute(new SendMessage(getChatId(update.message()), INFO_TEXT));

    }

    private Long getChatId(Message message) {
        return message.chat().id();
    }


}

