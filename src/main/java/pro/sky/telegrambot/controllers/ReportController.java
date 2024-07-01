package pro.sky.telegrambot.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.service.entities.ReportService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/volunteer")
@Tag(name = "Отчеты", description = "Эндпойнты для работы волонтера с отчетами итп")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    @Operation(summary = "Добавление отчета")
    public Report create(@Valid @RequestBody Report report) {
        return reportService.create(report);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение отчета")
    public Report update(@PathVariable long id, @RequestBody Report report) {
        return reportService.update(id, report);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление отчета")
    public Report delete(@PathVariable long id) {
        return reportService.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение отчета")
    public Report get(@PathVariable long id) {
        return reportService.get(id);
    }

    @GetMapping("/allreports")
    @Operation(summary = "Показать все отчеты")
    public ResponseEntity<Collection<Report>> findAll() {
        List<Report> reports = reportService.findAll();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}/reports")
    @Operation(summary = "Показать все отчеты усыновителя")
    public List<Report> getReports(@PathVariable long id) {

        return reportService.getReports(id);
    }

    @GetMapping("/badreport/{id}")
    @Operation(summary = "Выслать предупредительную месагу юзеру")
    public ResponseEntity<String> sendBadNotification(@PathVariable long id) {
        reportService.sendBadNotification(id);
        return ResponseEntity.ok().build();
    }
}
