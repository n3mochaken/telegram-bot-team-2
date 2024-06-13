package pro.sky.telegrambot.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.service.entities.AnimalService;

import java.io.IOException;


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

    @PutMapping("/{id}")
    @Operation(summary = "Изменение данных о животном в приюте")
    public Animal update(@PathVariable long id, @RequestBody Animal animal) {
        return animalService.update(id, animal);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление животного из приюта")
    public Animal delete(@PathVariable long id) {
        return animalService.delete(id);
    }

    @PostMapping(value = "/{id}/avatar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar)throws IOException{
        if (avatar.getSize()>=2048*2048){
            return ResponseEntity.badRequest().body("Слишком большая картинка");
        }
        animalService.uploadAvatar(id,avatar);
        return ResponseEntity.ok().build();

    }


/*
    @GetMapping
    @Operation(summary = "Показать всех животных приюта")
    public List<Animal> findAll(@RequestParam Animal animal) {
        return animalService.findAll(animal);
    }
*/

}
