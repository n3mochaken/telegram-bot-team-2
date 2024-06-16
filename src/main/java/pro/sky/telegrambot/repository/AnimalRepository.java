package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.entity.Animal;

import java.util.List;

/**
 * Репозиторий для работы с животными
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
