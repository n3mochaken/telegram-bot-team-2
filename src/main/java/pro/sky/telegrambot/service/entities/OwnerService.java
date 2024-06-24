package pro.sky.telegrambot.service.entities;


import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Owner;
import pro.sky.telegrambot.exception.OwnerNotFoundException;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.OwnerRepository;

import java.util.List;
import java.util.Optional;


/**
 * Сервис для работы с пользователями
 */
@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    /**
     * Добавляет заданную сущность
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     *
     * @param owner сохраняемая сущность
     * @return сохраняет сущность
     */
    public Owner create(Owner owner) {
        owner.setId(null);
        logger.info("Усыновитель создан");
        return ownerRepository.save(owner);
    }

    /**
     * Обновляет сущность по передоваемым параметрам
     * Используется метод репозитория {@link OwnerRepository#findById(Object)}
     *
     * @param id    индификатор сущности
     * @param owner сущность
     * @return измененную сущность
     */
    public Owner update(long id, Owner owner) {
        return ownerRepository.findById(id)
                .map(oldOwner -> {
                    oldOwner.setAnimal(owner.getAnimal());
                    oldOwner.setPhoneNumber(owner.getPhoneNumber());
                    oldOwner.setFullName(owner.getFullName());
                    logger.info("Изменены данный о усыновителе");
                    return ownerRepository.save(oldOwner);
                })
                //добавить логер logger.info("Усыновитель не найдено");
                .orElseThrow(() -> new OwnerNotFoundException(id));
    }

    /**
     * Удаляет сущность из приюта по идентификатору
     * Используются методы репозитория {@link OwnerRepository#findById(Object)}
     * и {@link OwnerRepository#delete(Object)}
     *
     * @param id индентификатор удаляемой сущности
     * @return удаленная сущность
     */
    public Owner delete(long id) {
        return ownerRepository.findById(id)
                .map(owner -> {
                    ownerRepository.delete(owner);
                    logger.info("Усыновитель удален");
                    return owner;
                })
                //добавить логер logger.info("Усыновитель не найден");
                .orElseThrow(() -> new OwnerNotFoundException(id));
    }

    /**
     * Возвращает сущность по её идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     *
     * @param id идентификатор сущности
     * @return возвращаемая сущность
     */
    public Owner get(long id) {
        logger.info("Найден усыновитель");
        return ownerRepository.findById(id)
                //добавить логер logger.info("Усыновитель не найден");
                .orElseThrow(() -> new OwnerNotFoundException(id));
    }

    /**
     * Создает нового пользователя и помещает в базу данных
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     *
     * @param update данные от пользователя
     */
    public void createOwner(Update update) {
        if (findPerson(update.message().chat().id()).isEmpty()) {
            Owner person = new Owner();
            person.setChatId(update.message().chat().id());
            ownerRepository.save(person);
            logger.info("Я СОЗДАЛ ПЕРСОНУ");
        } else {
            logger.info("ТАКОЙ ЧЕЛ ЕСТЬ");
        }
    }

//вопросик по двум методам ^выше 4стандартных метода круд (с возвращаемым овнером) \/ниже методы написанные без возвращаемого значения
    /**
     * Возвращает пользователя по его индентификатору
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     *
     * @param id индентификатор пользователя
     * @return возвращаемая сущность
     */
    public Optional<Owner> findPerson(long id) {
        logger.debug("Запуск метода поиска усыновителя");
        return ownerRepository.findByChatId(id);
    }

    /**
     * Возвращает список всех созданных сущностей
     *
     * @return список сущностей
     */
    public List<Owner> findAll() {
        logger.info("Вызван метод нахождения списка усыновителей");
        return ownerRepository.findAll();
    }

    public Optional<Owner> findByChatId(long id) {
        return ownerRepository.findByChatId(id);
    }

}
