package pro.sky.telegrambot.service.entities;

import com.pengrad.telegrambot.TelegramBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.exception.AnimalNotFoundException;
import pro.sky.telegrambot.repository.AnimalRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;


import static java.nio.file.StandardOpenOption.CREATE_NEW;


/**
 * Сервис для работы с животными
 */
@Transactional
@Service
public class AnimalService {

    @Value("${animal.avatars.path}")
    private String avatarPath;

    private TelegramBot bot;
    private final AnimalRepository animalRepository;

    private final Logger logger = LoggerFactory.getLogger(AnimalService.class);

    public AnimalService(TelegramBot bot, AnimalRepository animalRepository) {
        this.bot = bot;
        this.animalRepository = animalRepository;
    }

    /**
     * Добавляет заданную сущность
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     *
     * @param animal сохраняемая сущность
     * @return сохраняет сущность
     */
    public Animal create(Animal animal) {
        //animal.setId(null);
        logger.info("Животное создано");
        return animalRepository.save(animal);
    }

    /**
     * Обновляет сущность по передоваемым параметрам
     * Используется метод репозитория {@link AnimalRepository#findById(Object)}
     *
     * @param id     индификатор сущности
     * @param animal сущность
     * @return измененную сущность
     */
    public Animal update(long id, Animal animal) {
        return animalRepository.findById(id)
                .map(oldAnimal -> {
                    oldAnimal.setName(animal.getName());
                    oldAnimal.setName(animal.getName());
                    logger.info("Изменены данный о животном");
                    return animalRepository.save(oldAnimal);
                })
                //добавить логер logger.info("Животное не найдено");
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }

    /**
     * Удаляет сущность из приюта по идентификатору
     * Используются методы репозитория {@link AnimalRepository#findById(Object)}
     * и {@link AnimalRepository#delete(Object)}
     *
     * @param id индентификатор удаляемой сущности
     * @return удаленная сущность
     */
    public Animal delete(long id) {
        return animalRepository.findById(id)
                .map(animal -> {
                    animalRepository.delete(animal);
                    logger.info("Животное удалено");
                    return animal;
                })
                //добавить логер logger.info("Животное не найдено");
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }

    /**
     * Возвращает сущность по её идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     *
     * @param id идентификатор сущности
     * @return возвращаемая сущность
     */
    public Animal get(long id) {
        //добавить логер logger.info("Животное найдено");
        return animalRepository.findById(id)
                //добавить логер logger.info("Животное не найдено");
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }

    /**
     * Возвращает список всех созданных сущностей
     *
     * @return список сущностей
     */
    public List<Animal> findAll() {
        logger.info("Вызван метод нахождения списка животных");
        return animalRepository.findAll();
    }

    /**
     * Метод на входе принимает ид животного и файл, считывает файл,
     * сохраняет его на диск и записывает путь к нему в рупозиторий{@link AnimalRepository}
     *
     * @param id     животного из репозитория
     * @param avatar фотка,которую хотим прикрепить
     * @throws IOException дефолт
     */
//    public void uploadAvatar(Long id, MultipartFile avatar) throws IOException {
//
//        //добавить логи методу
//        Animal animal = animalRepository.getById(id);
//
//        Path filePath = Path.of(avatarPath, id + "." + getExtension(avatar.getOriginalFilename()));
//        Files.deleteIfExists(filePath);
//        try (InputStream is = avatar.getInputStream();
//             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
//
//        ) {
//            bis.transferTo(bos);
//        }
//
//        animal.setPhotoPass(filePath.toString());
//        animalRepository.save(animal);
//    }

    /**
     * Вспомогательный метод для определения разрешения загруженного фото
     *
     * @param fileName полученный от юзера файл
     * @return разрешение файла
     */
//    private String getExtension(String fileName) {
//        return fileName.substring(fileName.lastIndexOf(".") + 1);
//    }


    //метод нахождения всех усыновленных животных
    // метод нахождения животных на испытательном сроке

}
