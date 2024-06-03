package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.DeleteMessage;
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
        InlineKeyboardButton infoVolunteer = new InlineKeyboardButton("Вызов Волонтера").callbackData(VOLUNTEER);
        InlineKeyboardButton infoBtn = new InlineKeyboardButton("Информация о приютах").callbackData(INFO);
        keyboardMarkup.addRow(infoBtn);
        keyboardMarkup.addRow(infoVolunteer);

        SendMessage message = new SendMessage(update.message().chat().id(), START_TEXT);
        message.replyMarkup(keyboardMarkup);
        bot.execute(message);

        DeleteMessage deleteMessage = new DeleteMessage(update.message().chat().id(), update.message().messageId());
        BaseResponse response = bot.execute(deleteMessage);
    }


    public void infoPr(Update update) {

        bot.execute(new SendMessage(update.callbackQuery().message().chat().id(), INFO_TEXT));
    }

    public void volunteerCommand(Update update) {
        bot.execute(new SendMessage(update.callbackQuery().message().chat().id(), VOLUNTEER_TEXT));
    }

    private Long getChatId(Message message) {
        return message.chat().id();
    }
}

