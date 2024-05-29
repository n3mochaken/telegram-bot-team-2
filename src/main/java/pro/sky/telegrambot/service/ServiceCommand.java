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

    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        try {
            bot.execute(sendMessage);
        } catch (Exception e) {
            logger.error("Error sending message", e);
        }
    }

    public void startCommand(long chatId) {
        String text = "Привет!\n" +
                "\nЯ бот приютитель, могу помочь тебе приютить животное." +
                "\nУ меня есть вся подробная информация о процессе взаимодейсвия с приютами.";

        sendMessage(chatId, text);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton infoBtn = new InlineKeyboardButton("Информация о приютах").callbackData(INFO);
        keyboardMarkup.addRow(infoBtn);

        SendMessage sendMessage1 = new SendMessage(chatId, "Выберите , что вам необходимо!");
        sendMessage1.replyMarkup(keyboardMarkup);

        bot.execute(sendMessage1);

        // Доработаю!!!!

        // Тут много мата XD

    }

    public void infoPr(long chatId) {

        bot.execute(new SendMessage(chatId, "Здесь информация о приютах, которую вы хотите показать пользователю."));
    }

    public void helpCommand(long chatId) {

    }

    private Long getChatId(Message message){
            return message.chat().id();
    }
}

