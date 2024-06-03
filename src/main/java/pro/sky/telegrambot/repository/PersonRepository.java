package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
