package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot bot;

    @PostConstruct
    public void init() {
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            Map<String, Consumer<Long>> commandMap = new HashMap<>();

            commandMap.put(START, chatId -> {startCommand(chatId, START);
                logger.info("Command called - Start");
            });

                    // Checking the message
                    if (update.message() != null && update.message().text() != null) {
                        String message = update.message().text();
                        long chatId = update.message().chat().id();

                        // Checking the command in HashMap
                        if (commandMap.containsKey(message)) {
                            commandMap.get(message).accept(chatId);
                        }
                    }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    //--------------------------------------------------------------------

    // Command constants
    private static final String START = "/start";

    // Сommands for the bot
    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
       try {
           bot.execute(sendMessage);
       } catch (Exception e) {
           logger.error("Error sending message", e);
       }
    }

    public void startCommand(long chatId, String userName) {
        String text = "Добро пожаловать в бот! \n" +
                "\nИспользуйте команду - /help для дальнейшей инструкции!";

        String formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
    }


}
