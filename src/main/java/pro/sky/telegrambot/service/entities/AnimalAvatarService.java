package pro.sky.telegrambot.service.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.entity.AnimalAvatar;
import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.repository.AvatarRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

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

    public AnimalAvatarService(AnimalService animalService, AvatarRepository avatarRepository) {
        this.animalService = animalService;
        this.avatarRepository = avatarRepository;
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
}
