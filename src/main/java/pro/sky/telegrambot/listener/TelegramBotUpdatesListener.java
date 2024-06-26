package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.ReportRepository;
import pro.sky.telegrambot.entity.Owner;
import pro.sky.telegrambot.repository.OwnerRepository;
import pro.sky.telegrambot.service.ServiceCommand;
import pro.sky.telegrambot.service.entities.AnimalAvatarService;
import pro.sky.telegrambot.service.entities.AnimalService;
import pro.sky.telegrambot.service.entities.OwnerService;


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
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private TelegramBot bot;
    private ServiceCommand service;
    private OwnerService ownerService;
    private ReportRepository reportRepository;
    private AnimalAvatarService animalAvatarService;
    private OwnerRepository ownerRepository;
    private AnimalService animalService;

    public TelegramBotUpdatesListener(TelegramBot bot,
                                      ServiceCommand service,
                                      OwnerService ownerService,
                                      ReportRepository reportRepository,
                                      AnimalAvatarService animalAvatarService,
                                      OwnerRepository ownerRepository,
                                      AnimalService animalservice) {
        this.bot = bot;
        this.service = service;
        this.ownerService = ownerService;
        this.reportRepository = reportRepository;
        this.animalAvatarService = animalAvatarService;
        this.ownerRepository = ownerRepository;
        this.animalService = animalservice;
    }

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
                service.welcomeMenu(update);
                logger.info("Command called - /start");
            });

            commandMap.put(CALL_BACK_FOR_MAIN_MENU, chatId -> {
                service.mainMenu(update);
                logger.info("Command called - CALL_BACK_FOR_MAIN_MENU");
            });

            commandMap.put(CALL_BACK_FOR_TO_SUBMIT_THE_REPORT, chatId -> {
                service.submitToReport(update);
                logger.info("Command called - CALL_BACK_FOR_TO_SUBMIT_THE_REPORT");
            });

            commandMap.put(CALL_BACK_FOR_INFO, chatId -> {
                service.infoMenu(update);
                logger.info("Command called - CALL_BACK_FOR_INFO");
            });

            commandMap.put(CALL_BACK_FOR_VOLUNTEER, chatId -> {
                service.volunteerCommand(update);
                logger.info("Command called - CALL_BACK_FOR_VOLUNTEER");
            });

            commandMap.put(CALL_BACK_FOR_GENERAL_INFO_FILE, chatId -> {
                service.sendFileToUser(update);
                logger.info("Command called - CALL_BACK_FOR_GENERAL_INFO_FILE");
            });

            commandMap.put(CALL_BACK_FOR_CONSULTATION, chatId -> {
                service.consultationMenu(update);
                logger.info("Command called - CALL_BACK_FOR_CONSULTATION");
            });

            commandMap.put(CALL_BACK_FOR_ADDRESS, chatId -> {
                service.sendAddressToUser(update);
                logger.info("Command called - CALL_BACK_FOR_ADDRESS");
            });

            commandMap.put(CALL_BACK_FOR_CONTACTS, chatId -> {
                service.getContacts(update);
                logger.info("Command called - CALL_BACK_FOR_ADDRESS");
            });

            commandMap.put(CALL_BACK_FOR_SAFETY_RULES, chatId -> {
                service.sendFileToUser(update);
                logger.info("Command called - CALL_BACK_FOR_ADDRESS");
            });

            commandMap.put(CALL_BACK_FOR_TIMING, chatId -> {
                service.getTiming(update);
                logger.info("Command called - CALL_BACK_FOR_ADDRESS");
            });

            commandMap.put(CALL_BACK_FOR_RECOMMENDATIONS, chatId -> {
                service.recommendationsMenu(update);
                logger.info("Command called - CALL_BACK_FOR_RECOMMENDATIONS");
            });

            commandMap.put(CALL_BACK_FOR_RECORD_CONTACTS, chatId -> {
                service.requestContact(update);
                logger.info("Command called - CALL_BACK_FOR_RECORD_CONTACTS");
            });

            commandMap.put(CALL_BACK_FOR_LOOK_ANIMAL, chatId -> {
                animalService.sendAnimalInfo(update);
                logger.info("Command called - CALL_BACK_FOR_LOOK_ANIMAL");
            });

            commandMap.put(CALL_BACK_FOR_TRANSPORTATION_RECOMMENDATIONS, chatId -> {
                service.transportationRecommendations(update);
                logger.info("Command called - CALL_BACK_FOR_TRANSPORTATION_RECOMMENDATIONS");
            });

            commandMap.put(CALL_BACK_FOR_HOME_IMPROVEMENT_RECOMMENDATIONS_PUPPY, chatId -> {
                service.homeImprovementRecommendationsPuppy(update);
                logger.info("Command called - CALL_BACK_FOR_HOME_IMPROVEMENT_RECOMMENDATIONS_PUPPY");
            });

            commandMap.put(CALL_BACK_FOR_HOME_IMPROVEMENT_RECOMMENDATIONS_ADULT_PUPPY, chatId -> {
                service.homeImprovementRecommendationsAdultPuppy(update);
                logger.info("Command called - CALL_BACK_FOR_HOME_IMPROVEMENT_RECOMMENDATIONS_PUPPY");
            });

            commandMap.put(CALL_BACK_FOR_HOME_IMPROVEMENT_RECOMMENDATIONS_DISABLED, chatId -> {
                service.homeImprovementRecommendationsDisabled(update);
                logger.info("Command called - CALL_BACK_FOR_HOME_IMPROVEMENT_RECOMMENDATIONS_DISABLED");
            });

            commandMap.put(CALL_BACK_FOR_ADVICE_DOG_HANDLER, chatId -> {
                service.adviceDogHandler(update);
                logger.info("Command called - CALL_BACK_FOR_ADVICE_DOG_HANDLER");
            });

            commandMap.put(CALL_BACK_FOR_RULES_AND_SHELTER, chatId -> {
                service.rulesAndShelter(update);
                logger.info("Command called - CALL_BACK_FOR_RULES_AND_SHELTER");
            });

            commandMap.put(CALL_BACK_FOR_LIST_DOCUMENTS, chatId -> {
                service.listDocuments(update);
                logger.info("Command called - CALL_BACK_FOR_LIST_DOCUMENTS");
            });

            commandMap.put(CALL_BACK_FOR_REASONS_FOR_REFUSAL, chatId -> {
                service.reasonsForRefusal(update);
                logger.info("Command called - CALL_BACK_FOR_REASONS_FOR_REFUSAL");
            });

            commandMap.put(CALL_BACK_FOR_PROVEN_DOG_HANDLERS, chatId -> {
                service.provenDogHandlers(update);
                logger.info("Command called - CALL_BACK_FOR_PROVEN_DOG_HANDLERS");
            });

            if (update.message() != null) {
                // Проверка на наличие фотографии
                if (update.message().photo() != null && update.message().caption() != null) {
                    animalAvatarService.uploadReport(update);
                } else if (update.message().contact() != null) {
                    Owner owner = ownerService.findByChatId(update.message().chat().id()).orElseThrow(()
                    -> new RuntimeException("Owner not found for chatId: " + update.message().chat().id()));
                    String phoneNumber = update.message().contact().phoneNumber();
                    owner.setPhoneNumber(phoneNumber);
                    ownerRepository.save(owner);
                    bot.execute(new SendMessage(update.message().chat().id(), "Номер телефона добавлен - " + phoneNumber));
                } else if (update.message().text() != null) {
                    ownerService.createOwner(update);
                    String message = update.message().text();
                    long chatId = update.message().chat().id();
                    if (commandMap.containsKey(message)) {
                        commandMap.get(message).accept(chatId);
                    } else {
                        bot.execute(new SendMessage(chatId, "Не понял тебя месага"));
                    }
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
                } else {
                    bot.execute(new SendMessage(update.message().chat().id(), "Не понял тебя апдейт"));
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
