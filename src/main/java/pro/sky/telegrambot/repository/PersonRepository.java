package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Person;

import java.util.Optional;

/**
 * Репозиторий для пользователей
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByChatId(long chatId);

}
