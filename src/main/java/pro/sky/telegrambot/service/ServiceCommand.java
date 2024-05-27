package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;

import static pro.sky.telegrambot.constants.Constants.INFO;


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

    public void startCommand(long chatId, String message) {
        String text = "Привет!." +
                "\nЯ бот приютитель, могу помочь тебе приютить животное." +
                "\nУ меня есть вся подробная информация о процессе взаимодейсвия с приютами." +
                "\nЕсли тебе нуждна помощь напиши команду \n/help";

        String formattedText = String.format(text, message);
        sendMessage(chatId, formattedText);
    }

    public void infoPr(long chatId) {

        bot.execute(new SendMessage(chatId, "Здесь информация о приютах, которую вы хотите показать пользователю."));
    }

    public void helpCommand(long chatId) {

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton infoBtn = new InlineKeyboardButton("Информация о приютах").callbackData("/info");
        InlineKeyboardButton volonterBtn = new InlineKeyboardButton("Волонтер").callbackData("Volonter");
        keyboardMarkup.addRow(infoBtn);
        keyboardMarkup.addRow(volonterBtn);

        SendMessage sendMessage1 = new SendMessage(chatId, "Выбери кнопку");
        sendMessage1.replyMarkup(keyboardMarkup);
        bot.execute(sendMessage1);

    }

    private Long getChatId (Message message){
            return message.chat().id();
    }
}

