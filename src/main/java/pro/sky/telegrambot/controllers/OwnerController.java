package pro.sky.telegrambot.controllers;

import com.pengrad.telegrambot.model.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.entity.Owner;
import pro.sky.telegrambot.repository.OwnerRepository;
import pro.sky.telegrambot.service.entities.OwnerService;

import java.util.List;

/**
 * Контроллер для работы с усыновителями животных
 */
@RestController
@RequestMapping("/owner")
@Tag(name = "Усыновители", description = "Эндпойнты для работы с усыновителями")
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerRepository ownerRepository;

    public OwnerController(OwnerService ownerService, OwnerRepository ownerRepository) {
        this.ownerService = ownerService;
        this.ownerRepository = ownerRepository;
    }

    @PostMapping
    @Operation(summary = "Добавление усыновителя")
    public Owner create(@RequestBody Owner owner) {
        return ownerService.create(owner);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение данных об усыновителе")
    public Owner update(@PathVariable long id, @RequestBody Owner owner) {
        return ownerService.update(id, owner);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление усыновителя")
    public Owner delete(@PathVariable long id) {
        return ownerService.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение усыновителя")
    public Owner get(@PathVariable long id) {
        return ownerService.get(id);
    }

    @GetMapping
    @Operation(summary = "Получение всех усыновителей")
    public ResponseEntity<List<Owner>> findAll() {
        List<Owner> owners = ownerService.findAll();
        return ResponseEntity.ok(owners);
    }
}
