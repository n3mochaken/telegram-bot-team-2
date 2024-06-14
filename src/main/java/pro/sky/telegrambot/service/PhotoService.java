package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Report;
//import pro.sky.telegrambot.repository.ReportRepository;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

@Service
public class PhotoService {

    private Logger logger = LoggerFactory.getLogger(PhotoService.class);

    @Autowired
    private TelegramBot bot;

//    @Autowired
//    private ReportRepository reportRepository;

    public void processPhoto(Update update) {
        logger.info("Вызван метод processPhoto");

        bot.execute(new SendMessage(update.message().chat().id(), "Забрал фото"));

        var photos = update.message().photo();
        var largestPhoto = photos[photos.length - 1];
        var fileId = largestPhoto.fileId();

        GetFile getFileRequest = new GetFile(fileId);
        GetFileResponse getFileResponse = bot.execute(getFileRequest);

        if (getFileResponse.isOk()) {
            logger.info("Скачиваю файл");
            String filePath = getFileResponse.file().filePath();
            String fileUrl = "https://api.telegram.org/file/bot" + bot.getToken() + "/" + filePath;
            try (InputStream fileStream = new URL(fileUrl).openStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] photoBytes = outputStream.toByteArray();
                Report report = new Report();
                report.setPhoto(photoBytes);

                logger.info("Пытаюсь кинуть в репу");
//                reportRepository.save(report);

                bot.execute(new SendMessage(update.message().chat().id(), "Фото успешно загружено и сохранено."));
            } catch (Exception e) {
                logger.error("Ошибка при сохранении фото", e);
                bot.execute(new SendMessage(update.message().chat().id(), "Ошибка при сохранении фото."));
            }
        }
    }
}

