package pro.sky.telegrambot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.repository.ReportRepository;
import pro.sky.telegrambot.service.entities.ReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pro.sky.telegrambot.ConstantsTest.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    Logger logger;

    @Mock
    ReportRepository repository;

    @InjectMocks
    ReportService service;

    @BeforeEach
    void setup() {
        service = new ReportService(repository, logger);
    }

    @Test
    void reportCreateTest() {
        Report expected = new Report(REPORT_ID_1, PHOTO_1, FOOD_1, HEALTH_1, CHANGES_1, true, null);
        when(repository.save(expected)).thenReturn(expected);

        Report actual = service.create(expected);

        verify(repository, only()).save(expected);
        verify(logger).info("Отчет создан");
        assertEquals(expected, actual);
    }

    @Test
    void updateReportTest() {
        // не работает - надо думать!
        Report newReportData = new Report(REPORT_ID_1, PHOTO_1, FOOD_1, HEALTH_1, CHANGES_1, APPROVAL_1, null);

        Report existingReport = new Report(REPORT_ID_1, PHOTO_1, FOOD_1, HEALTH_1, CHANGES_1, APPROVAL_1, null);
        when(repository.findById(REPORT_ID_1)).thenReturn(Optional.of(existingReport));

        Report updatedReport = new Report(REPORT_ID_1, PHOTO_1, FOOD_1, HEALTH_1, CHANGES_1, APPROVAL_1, null);
        when(repository.save(any(Report.class))).thenReturn(updatedReport);

        Report actual = service.update(REPORT_ID_1, newReportData);

        verify(repository, times(1)).findById(REPORT_ID_1);
        verify(repository, times(1)).save(updatedReport);

        assertEquals(updatedReport, actual);
    }

    @Test
    void deleteReportTest() {
        Report expected = new Report(REPORT_ID_1, PHOTO_1, FOOD_1, HEALTH_1, CHANGES_1, APPROVAL_1, null);
        when(repository.findById(REPORT_ID_1)).thenReturn(Optional.of(expected));

        service.delete(REPORT_ID_1);

        assertTrue(repository.findById(REPORT_ID_1).isPresent());
    }

    @Test
    void getReportTest() {
        Report expected = new Report(REPORT_ID_1, PHOTO_1, FOOD_1, HEALTH_1, CHANGES_1, APPROVAL_1, null);
        when(repository.findById(REPORT_ID_1)).thenReturn(Optional.of(expected));

        Report actual = service.get(REPORT_ID_1);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testFindAllReports() {
        List<Report> expectedReports = new ArrayList<>();
        expectedReports.add(new Report(REPORT_ID_1, PHOTO_1, FOOD_1, HEALTH_1, CHANGES_1, APPROVAL_1, null));
        expectedReports.add(new Report(REPORT_ID_2, PHOTO_2, FOOD_2, HEALTH_2, CHANGES_1, APPROVAL_2, null));

        when(repository.findAll()).thenReturn(expectedReports);

        List<Report> actualReports = service.findAll();

        assertEquals(expectedReports, actualReports);
    }

    @Test
    void getReportsTest() {
        //  не работает - надо думать!

        // Создаем фиктивный отчет
        Report mockReport = new Report();
        mockReport.setId(REPORT_ID_1);

        // Метод get должен возвращать этот фиктивный отчет
        when(repository.getById(REPORT_ID_1)).thenReturn(mockReport);

        // Создаем фиктивный список отчетов, в котором должен быть только один отчет с id = 1
        List<Report> mockReportList = new ArrayList<>();
        mockReportList.add(mockReport);

        // Метод findAllReportById должен возвращать выше созданный список
        when(repository.findAllReportById(REPORT_ID_1)).thenReturn(mockReportList);

        // Вызываем тестируемый метод
        List<Report> result = service.getReports(REPORT_ID_1);

        // Проверяем, что результат содержит один отчет с id = 1
        assertEquals(1, result.size());
        assertEquals(REPORT_ID_1, result.get(0).getId());
    }
}
