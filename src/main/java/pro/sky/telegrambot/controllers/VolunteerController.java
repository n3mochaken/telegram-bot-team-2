package pro.sky.telegrambot.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.entity.Report;

import pro.sky.telegrambot.service.entities.VolunteerService;

import java.util.Collection;


@RestController
@RequestMapping("/volunteer")
@Tag(name = "Волонтер", description = "Эндпойнты для работы волонтера с отчетами итп")
public class VolunteerController {

private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }


    @GetMapping("/allreports")
    public ResponseEntity<Collection<Report>> getAllReports(){
       return ResponseEntity.ok(volunteerService.getAllReports());
    }
}
