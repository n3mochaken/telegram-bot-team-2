package pro.sky.telegrambot.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.service.AnimalService;


/**
 * Контроллер для работы с животными
 */
@RestController
@RequestMapping("/animal")
@Tag(name = "Животные", description = "Эндпойнты для работы с животными")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }


    @PostMapping
    @Operation(summary = "Добавление животного в приют")
    public Animal create(@RequestBody Animal animal) {
        return animalService.create(animal);
    }
}
