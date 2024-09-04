package pro.sky.telegrambot.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.entity.AnimalAvatar;
import pro.sky.telegrambot.service.entities.AnimalAvatarService;
import pro.sky.telegrambot.service.entities.AnimalService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import java.util.List;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Контроллер для работы с животными
 */
@RestController
@RequestMapping("/animal")
@Tag(name = "Животные", description = "Эндпойнты для работы с животными")
public class AnimalController {

    private final AnimalService animalService;

    private final AnimalAvatarService animalAvatarService;

    public AnimalController(AnimalService animalService, AnimalAvatarService animalAvatarService) {
        this.animalService = animalService;

        this.animalAvatarService = animalAvatarService;
    }

    @PostMapping
    @Operation(summary = "Добавление животного в приют")
    public Animal create(@Valid @RequestBody Animal animal) {
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


    @GetMapping("/{id}")
    @Operation(summary = "Получение животного")
    public Animal get(@PathVariable long id) {
        return animalService.get(id);
    }

    @GetMapping
    @Operation(summary = "Показать всех животных приюта")
    public ResponseEntity<List<Animal>> findAll() {
        List<Animal> animals = animalService.findAll();
        return ResponseEntity.ok(animals);
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "установка аватарки пета по ид")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 2048 * 4048) {
            return ResponseEntity.badRequest().body("Файл слишком большой");
        }
        animalAvatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar")
    @Operation(summary = "выгрузка аватараш шкуры")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        AnimalAvatar animalAvatar = animalAvatarService.findAnimalAvatar(id);

        Path path = Path.of(animalAvatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(animalAvatar.getMediaType());
            response.setContentLength((int) animalAvatar.getFileSize());
            is.transferTo(os);
        }
    }
}
