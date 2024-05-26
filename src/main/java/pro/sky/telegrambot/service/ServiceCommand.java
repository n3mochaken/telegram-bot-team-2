package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;

import java.util.ArrayList;
import java.util.List;




@Service
public class ServiceCommand  {

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

    public void startCommand(long chatId, String message) {
        String text = "Привет! %s." +
                "\nЯ бот приютитель, могу помочь тебе приютить животное." +
                "\nУ меня есть вся подробная информация о процессе взаимодейсвия с приютами." +
                "\n Если тебе нуждна помощь напиши команду - /help";

        String formattedText = String.format(text, message);
        sendMessage(chatId, formattedText);
    }

    public void helpCommand(long chatId, String message) {

    }
}
