package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.PersonRepository;
import pro.sky.telegrambot.service.ServiceCommand;
import pro.sky.telegrambot.service.entities.PersonService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static pro.sky.telegrambot.constants.Constants.*;


/**
 * Основной класс для работы с Телеграм.
 * Реализует интерфейс {@link UpdatesListener} для обработки обратного вызова с доступными обновлениями
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot bot;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    PersonService personService;

    @Autowired
    private ServiceCommand service;

    private final Map<String, Consumer<Long>> commandMap = new HashMap<>();

    @PostConstruct
    public void init() {
        bot.setUpdatesListener(this);
    }

    /**
     * Метод обратного вызова, вызываемый при обновлениях.
     * Под обновлением подразумевается действие, совершённое с ботом — например, получение сообщения от пользователя.
     * Метод обрабатывает полученные обновления и в зависимости от их типа (сообщение или обратный запрос от событий нажатия кнопок),
     * отправляет на соответствующие обработчики
     *
     * @param updates доступные обновления
     * @return {@code UpdatesListener.CONFIRMED_UPDATES_ALL = -1}
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            commandMap.put(START_COMMAND, chatId -> {
                service.startCommand(update);
                logger.info("Command called - /start");
            });
//            commandMap.put(INFO_COMMAND, chatId -> {
//                service.infoPr(update);
//                logger.info("Command called - /info");
//            });
//            commandMap.put(VOLUNTEER_COMMAND, chatId -> {
//                service.volunteerCommand(update);
//                logger.info("Command called - /volunteer");
//            });
            commandMap.put(CALL_BACK_GET_FILE_GENERAL, chatId -> {
                service.sendFileToUser(update);
                logger.info("Command called - CALL_BACK_GET_FILE_GENERAL");
            });
            commandMap.put(CALL_BACK_FOR_INFO, chatId -> {
                service.infoPr(update);
                logger.info("Command called - CALL_BACK_FOR_INFO");
            });
            commandMap.put(CALL_BACK_FOR_VOLUNTEER, chatId -> {
                service.volunteerCommand(update);
                logger.info("Command called - CALL_BACK_FOR_VOLUNTEER");
            });


            if (update.message() != null && update.message().text() != null && update.message().chat() != null) {
                personService.createPerson(update);
                logger.info("Мы получили апдейт, который не нулл");
                String message = update.message().text();
                long chatId = update.message().chat().id();
                // Checking the command in HashMap
                if (commandMap.containsKey(message)) {
                    commandMap.get(message).accept(chatId);
                }
            }
            if (update.callbackQuery() != null) {
                logger.info("Мы получили апдейт, колбэн не нулл");
                CallbackQuery callbackQuery = update.callbackQuery();
                String callbackData = callbackQuery.data();
                long chatId = update.callbackQuery().message().chat().id();
                // Checking the command in HashMap
                if (commandMap.containsKey(callbackData)) {
                    logger.info("Мы получили апдейт, колбэк есть в мапе");
                    commandMap.get(callbackData).accept(chatId);

                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
