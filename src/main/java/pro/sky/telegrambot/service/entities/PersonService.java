package pro.sky.telegrambot.service.entities;


import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Person;
import pro.sky.telegrambot.repository.PersonRepository;

import java.util.Optional;

/**
 * Сервис для работы с пользователями
 */
@Service

public class PersonService {

    private final PersonRepository personRepository;

    Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Создает нового пользователя и помещает в базу данных
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     *
     * @param update данные от пользователя
     */
    public void createPerson(Update update) {
        if (findPerson(update.message().chat().id()) == null) {
            Person person = new Person();
            person.setChatId(update.message().chat().id());
            personRepository.save(person);
            logger.info("Я СОЗДАЛ ПЕРСОНУ");
        } else {
            logger.info("ТАКОЙ ЧЕЛ ЕСТЬ");
        }
    }

    /**
     * Возвращает пользователя по его индентификатору
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     *
     * @param id индентификатор пользователя
     * @return возвращаемая сущность
     */
    public Optional<Person> findPerson(long id) {
        logger.debug("Running find student method");
        return personRepository.findByChatId(id);
    }

}
