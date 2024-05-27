package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.ServiceCommand;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static pro.sky.telegrambot.constants.Constants.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot bot;

    @Autowired
    private ServiceCommand service;

    @PostConstruct
    public void init() {
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            Map<String, Consumer<Long>> commandMap = new HashMap<>();

            commandMap.put(START, chatId -> {service.startCommand(chatId, START);
                logger.info("Command called - /start");
            });

            commandMap.put(HELP, chatId -> {service.helpCommand(chatId);
                logger.info("Command called - /help");
            });

            commandMap.put(INFO, chatId -> {service.infoPr(chatId);
                        logger.info("Command called - /info");
            });

                    // Checking the message
                    if (update.message() != null && update.message().text() != null) {
                        String message = update.message().text();
                        long chatId = update.message().chat().id();

                        // Checking the command in HashMap
                        // доработать
                        if (commandMap.containsKey(message)) {
                            commandMap.get(message).accept(chatId);
                        }
                    }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
