package pro.sky.telegrambot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class TelegramBotConfiguration {

    @Value("${telegram.bot.name}")
    private String botName;

    @Value("${telegram.bot.token}")
    private String token;


    /**
     * Создает {@link Bean} получив токен из файла свойств
     *
     * @return {@link Bean} очищенный от команд Телеграм Бот
     */
    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());

        try {
            // Получение всех накопившихся апдейтов
            GetUpdates getUpdates = new GetUpdates().limit(100).offset(0);
            GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
            while (!updatesResponse.updates().isEmpty()) {
                // Устанавливаем offset на максимальный полученный update_id + 1
                int lastUpdateId = updatesResponse.updates().get(updatesResponse.updates().size() - 1).updateId();
                getUpdates.offset(lastUpdateId + 1);
                updatesResponse = bot.execute(getUpdates);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bot;


    }





}
