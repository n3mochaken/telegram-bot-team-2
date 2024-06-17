package pro.sky.telegrambot.service.entities;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import liquibase.pro.packaged.O;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.entity.AnimalAvatar;
import pro.sky.telegrambot.entity.Owner;
import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.repository.AvatarRepository;
import pro.sky.telegrambot.repository.OwnerRepository;
import pro.sky.telegrambot.repository.ReportRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static liquibase.util.file.FilenameUtils.getExtension;

@Service
@Transactional
public class AnimalAvatarService {
    private final Logger logger = LoggerFactory.getLogger(AnimalService.class);

    @Value("${animal.avatars.path}")
    String avatarPath;

    private final AnimalService animalService;
    private final AvatarRepository avatarRepository;
    private final ReportRepository reportRepository;
    private final TelegramBot bot;
    private final OwnerRepository ownerRepository;


    public AnimalAvatarService(OwnerRepository ownerRepository, AnimalService animalService, AvatarRepository avatarRepository, TelegramBot bot, ReportRepository reportRepository) {

        this.animalService = animalService;
        this.reportRepository = reportRepository;
        this.avatarRepository = avatarRepository;
        this.bot = bot;
        this.ownerRepository=ownerRepository;
    }

    public AnimalAvatar findAnimalAvatar(Long animalId) {
        return avatarRepository.findById(animalId).orElse(new AnimalAvatar());
    }

    public void uploadAvatar(Long id, MultipartFile avatar) throws IOException {
        logger.info("Получил картинку в сервис");

        Animal animal = animalService.get(id);

        Path filePath = Path.of(avatarPath, id + "." + getExtension(avatar.getOriginalFilename()));

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = avatar.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);

        ) {
            bis.transferTo(bos);
            logger.info("СОЗДАЛ ФАЙЛ, ВСЕ ПО КАЙФУ");
        }

        AnimalAvatar animalAvatar = findAnimalAvatar(id);
        animalAvatar.setAnimal(animal);
        animalAvatar.setFilePath(filePath.toString());
        animalAvatar.setFileSize(avatar.getSize());
        animalAvatar.setMediaType(avatar.getContentType());
        animalAvatar.setPreview(generateImagePreview(filePath));
        animal.setPhotoPass(String.valueOf(filePath));
        avatarRepository.save(animalAvatar);
        logger.info("Закончили упражнение");
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);

    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        logger.info("залез делать превью");
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            logger.info("я сделял превью");
            return baos.toByteArray();


        }
    }
/**
Метод принимает
 @param update, если от пользователдя пришло фото с текстом в одном сообщении.
 Сохраняет в репозиторий фото и текст.
 */

    public void uploadReport(Update update)   {
        long chatId = update.message().chat().id();
        logger.info("Получил картинку в сервис");
        String message = update.message().caption();

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

                Owner owner = ownerRepository.findByChatId(chatId).orElseThrow(null);
                byte[] photoBytes = outputStream.toByteArray();
                Report report = new Report();
                report.setPhoto(photoBytes);
                report.setOwner(owner);
                report.setFood(message);

                logger.info("Пытаюсь кинуть в репу");
                reportRepository.save(report);

                bot.execute(new SendMessage(update.message().chat().id(), "Фото успешно загружено и сохранено."));
            } catch (Exception e) {
                logger.error("Ошибка при сохранении фото", e);
                bot.execute(new SendMessage(update.message().chat().id(), "Ошибка при сохранении репорта."));
            }

        }
    }



}
