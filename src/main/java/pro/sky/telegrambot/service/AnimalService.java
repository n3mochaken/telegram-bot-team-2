package pro.sky.telegrambot.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.exception.AnimalNotFoundException;
import pro.sky.telegrambot.repository.AnimalRepository;
import pro.sky.telegrambot.repository.PersonRepository;

import java.util.List;

/**
 * Сервис для работы с животными
 */
@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    Logger logger = LoggerFactory.getLogger(AnimalService.class);

    public AnimalService(AnimalRepository animalRepository) {
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
        animal.setAnimalId(null);
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
                    oldAnimal.setAnimalName(animal.getAnimalName());
                    oldAnimal.setAnimalAge(animal.getAnimalAge());
                    return animalRepository.save(oldAnimal);
                })
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
                    return animal;
                })
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }

/*    public List<Animal> findAll(Animal animal) {

    }*/

    //метод нахождение всех животных прияюта
    //метод нахождения всех усыновленных животных
    // метод нахождения животных на испытательном сроке

}
