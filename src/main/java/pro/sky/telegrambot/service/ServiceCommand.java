package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;

import java.io.File;

import static pro.sky.telegrambot.constants.Constants.*;

/**
 * Сервис реализующий команды Телеграм бота
 *
 */
@Service
public class ServiceCommand {

    @Value("${upload.path}")
    private String pathToDocument;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot bot;

    // Сommands for the bot

    /**
     * Стартует работу бота, предоставляет информацию о приютах и возможность вызова волонтера
     *
     * @param update данные от пользователя
     */
    public void welcomeMenu(Update update) {

        logger.info("Взван метод welcomeMenu");

        Long chatId = update.message().chat().id();
        Integer messageId = update.message().messageId();

        logger.info("МЕСАДЖ ТЕКСТ " + update.message().text());

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton menuBtn = new InlineKeyboardButton("Начать").callbackData(CALL_BACK_FOR_START_MENU);
        keyboardMarkup.addRow(menuBtn);

        SendMessage message = new SendMessage(update.message().chat().id(), START_TEXT);
        DeleteMessage deleteMessage = new DeleteMessage(chatId,messageId);
        message.replyMarkup(keyboardMarkup);

        logger.info("сделал клаву");

        bot.execute(message);
        bot.execute(deleteMessage);

        logger.info("отправил месагу");
    }

    public void mainMenu(Update update){

        logger.info("Взван метод mainMenu");

        Long chatId = update.callbackQuery().message().chat().id();
        Integer messageId = update.callbackQuery().message().messageId();

        logger.info("КАЛЛБЭК ТЕКСТ " + update.callbackQuery().data());

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton infoBtn = new InlineKeyboardButton("Информация о приюте").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoVolunteer = new InlineKeyboardButton("Вызов Волонтера").callbackData(CALL_BACK_FOR_VOLUNTEER);
        keyboardMarkup.addRow(infoBtn);
        keyboardMarkup.addRow(infoVolunteer);

        logger.info("сделал клаву");

        bot.execute(new EditMessageText(chatId,messageId,MAIN_MENU_TEXT));
        bot.execute(new EditMessageReplyMarkup(chatId,messageId).replyMarkup(keyboardMarkup));

        logger.info("отправил месагу");
    }


    public void infoMenu(Update update) {

        logger.info("Взван метод infoMenu");

        long chatId = update.callbackQuery().message().chat().id();
        Integer messageId = update.callbackQuery().message().messageId();

        logger.info("КАЛЛБЭК ТЕКСТ " + update.callbackQuery().data());

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton infoBtn1 = new InlineKeyboardButton("Как проехать").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoBtn2 = new InlineKeyboardButton("Контактные данные для оформления пропуска").callbackData(CALL_BACK_FOR_GENERAL_INFO_FILE);
        InlineKeyboardButton infoBtn3 = new InlineKeyboardButton("Техника безопасности на территории приюта").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoBtn4 = new InlineKeyboardButton("Расписание работы приюта").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoBtn5 = new InlineKeyboardButton("Получить консультацию").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoBtn6 = new InlineKeyboardButton("Вызвать волонтера").callbackData(CALL_BACK_FOR_INFO);

        InlineKeyboardButton infoBtn7 = new InlineKeyboardButton("Вернуться в главное меню").callbackData(CALL_BACK_FOR_START_MENU);

        logger.info("сделал кнопки");

        keyboardMarkup.addRow(infoBtn1);
        keyboardMarkup.addRow(infoBtn2);
        keyboardMarkup.addRow(infoBtn3);
        keyboardMarkup.addRow(infoBtn4);
        keyboardMarkup.addRow(infoBtn5);
        keyboardMarkup.addRow(infoBtn6);
        keyboardMarkup.addRow(infoBtn7);

        logger.info("сделал кнопки в ряд");

        logger.info("сделал клаву");

        bot.execute(new EditMessageText(chatId,messageId,INFO_TEXT));
        bot.execute(new EditMessageReplyMarkup(chatId,messageId).replyMarkup(keyboardMarkup));

        logger.info("отправил месагу");
    }

    /**
     * Вызов волонтера
     *
     * @param update данные от пользователя
     */
    public void volunteerCommand(Update update) {
        bot.execute(new SendMessage(update.callbackQuery().message().chat().id(), VOLUNTEER_TEXT));

    }

    public void sendFileToUser (Update update){

        logger.info("Запущен метод sendFileToUser");

        long chatId = update.callbackQuery().message().chat().id();

        SendMessage message = new SendMessage(chatId,"Начинаю отправку файла");
        Message responseMessage = bot.execute(message).message();

        logger.info("Зашел в метод отправки");

        File file = new File(pathToDocument + update.callbackQuery().data());

        logger.info("считал файл");

        SendDocument sendDocument = new SendDocument(chatId,file);
        sendDocument.caption("Вот информация, которую ты запросил!");
        bot.execute(sendDocument);

        logger.info("отправил файл");

        DeleteMessage deleteMessage = new DeleteMessage(chatId,responseMessage.messageId());
        bot.execute(deleteMessage);

    }

    public void getUserData(Update update) {
        logger.info("Взван метод getUserData");
        Long chatId = update.message().chat().id();
    }

    private Long getChatId(Message message) {
        return message.chat().id();
    }
}

