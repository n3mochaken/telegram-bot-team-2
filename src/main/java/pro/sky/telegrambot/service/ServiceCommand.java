package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
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
    public void startCommand(Update update) {

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton infoBtn = new InlineKeyboardButton("Информация о приюте").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoVolunteer = new InlineKeyboardButton("Вызов Волонтера").callbackData(CALL_BACK_FOR_VOLUNTEER);
        //InlineKeyboardButton fileBtn = new InlineKeyboardButton("Получить файл").callbackData(CALL_BACK_GET_FILE_GENERAL);

        keyboardMarkup.addRow(infoBtn);
        keyboardMarkup.addRow(infoVolunteer);
        //keyboardMarkup.addRow(fileBtn);

        SendMessage message = new SendMessage(update.message().chat().id(), START_TEXT);
        message.replyMarkup(keyboardMarkup);
        bot.execute(message);

        DeleteMessage deleteMessage = new DeleteMessage(update.message().chat().id(), update.message().messageId());
        BaseResponse response = bot.execute(deleteMessage);
    }


    public void infoPr(Update update) {
        logger.info("Взван метод infoPr");
        bot.execute(new SendMessage(update.callbackQuery().message().chat().id(), INFO_TEXT));
        logger.info("КАЛЛБЭК ТЕКСТ " + update.callbackQuery().data());

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton infoBtn1 = new InlineKeyboardButton("Как проехать").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoBtn2 = new InlineKeyboardButton("Контактные данные для оформления пропуска").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoBtn3 = new InlineKeyboardButton("Техника безопасности на территории приюта").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoBtn4 = new InlineKeyboardButton("Расписание работы приюта").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoBtn5 = new InlineKeyboardButton("Получить консультацию").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoBtn6 = new InlineKeyboardButton("Вызвать волонтера").callbackData(CALL_BACK_FOR_INFO);
        InlineKeyboardButton infoBtn7 = new InlineKeyboardButton("Вернуться в главное меню").callbackData(CALL_BACK_FOR_INFO);
        logger.info("сделал кнопки");

        keyboardMarkup.addRow(infoBtn1);
        keyboardMarkup.addRow(infoBtn2);
        keyboardMarkup.addRow(infoBtn3);
        keyboardMarkup.addRow(infoBtn4);
        keyboardMarkup.addRow(infoBtn5);
        keyboardMarkup.addRow(infoBtn6);
        keyboardMarkup.addRow(infoBtn7);
        logger.info("сделал кнопки в ряд");

        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(), "ТЕСТОВЫЙ ТЕКСТ");
        logger.info("сформировал месагу");
        message.replyMarkup(keyboardMarkup);
        bot.execute(message);
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

    public void sendFileToUser(Update update){
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),"Начинаю отправку файла");
        bot.execute(message);
        logger.info("Зашел в метод отправки");
        File file = new File(pathToDocument + "generalInfo.pdf");
        logger.info("считал файл");
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),file);
        sendDocument.caption("ВОТ ИНФА");
        bot.execute(sendDocument);
        logger.info("отправил файл");
    }

    public void getUserData(Update update) {
        logger.info("Взван метод getUserData");
        Long chatId = update.message().chat().id();

    }


    private Long getChatId(Message message) {
        return message.chat().id();
    }
}

