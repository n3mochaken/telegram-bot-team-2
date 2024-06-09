package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Photo;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.PhotoRepository;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

@Service
public class PhotoService {
    @Value("${download.path}")
    String downPass;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot bot;

    @Autowired
    private PhotoRepository photoRepository;

    public void processPhoto(Update update){

        logger.info("Взван метод processPhoto");

        bot.execute(new SendMessage(update.message().chat().id(),"Забрал фото"));

        var photos = update.message().photo();
        var largestPhoto = photos[photos.length - 1];
        var fileId = largestPhoto.fileId();


        GetFile getFileRequest = new GetFile(fileId);
        GetFileResponse getFileResponse = bot.execute(getFileRequest);

        if (getFileResponse.isOk()) {
            logger.info("Скачиваю файл");
            String filePath = getFileResponse.file().filePath();
            String fileUrl = "https://api.telegram.org/file/bot" + bot.getToken() + "/" + filePath;
            try {
                InputStream fileStream = new URL(fileUrl).openStream();
                String localFilePath = downPass + fileId+".jpg";
                FileOutputStream outputStream = new FileOutputStream(localFilePath);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                fileStream.close();
                logger.info("С файлом все норм");



                Photo photo = new Photo(filePath, update.message().chat().id());

                logger.info("Пытаюсь кинуть в репу");
                photoRepository.save(photo);

                bot.execute(new SendMessage(update.message().chat().id(), "Фото успешно загружено и сохранено."));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }

    }


