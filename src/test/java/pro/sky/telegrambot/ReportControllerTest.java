package pro.sky.telegrambot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.telegrambot.controllers.ReportController;
import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.repository.OwnerRepository;
import pro.sky.telegrambot.service.entities.ReportService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.sky.telegrambot.ConstantsTest.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportControllerTest {

    private List<Report> savedReports;
    private static final ObjectMapper mapper = new ObjectMapper();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReportController controller;

    @Autowired
    private ReportService service;

    @Autowired
    private OwnerRepository repository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void contextLoadsTest() throws Exception {
        Assertions.assertThat(controller).isNotNull();
    }

    @Test
    void createReportTest() throws JsonProcessingException, JSONException {
        Report report = new Report(REPORT_ID_1, PHOTO_1, FOOD_1, HEALTH_1, CHANGES_1, true, null);

        String expected = mapper.writeValueAsString(report);

        ResponseEntity<String> response = restTemplate.postForEntity("/volunteer", report, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void editReportTest() throws Exception {
        Report report = new Report(REPORT_ID_1, PHOTO_1, FOOD_1, HEALTH_1, CHANGES_1, true, null);

        HttpEntity<Report> entity = new HttpEntity<>(report);

    }

}
