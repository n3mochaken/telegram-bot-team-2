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
import org.springframework.http.*;
import pro.sky.telegrambot.controllers.AnimalController;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.repository.AnimalRepository;
import pro.sky.telegrambot.service.entities.AnimalService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.sky.telegrambot.ConstantsTest.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalControllerTest {

    private List<Animal> savedAnimals;
    private static final ObjectMapper mapper = new ObjectMapper();

    @LocalServerPort
    private int port;

    @Autowired
    private AnimalController controller;

    @Autowired
    private AnimalService service;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AnimalRepository repository;

    @BeforeEach
    public void setUp() {
        Animal animal1 = new Animal(ANIMAL_ID_1, ANIMAL_NAME_1, ANIMAL_AGE_1, null);
        Animal animal2 = new Animal(ANIMAL_ID_2, ANIMAL_NAME_2, ANIMAL_AGE_2, null);
        List<Animal> animals = List.of(animal1, animal2);

        savedAnimals = repository.saveAll(animals);
    }

    @Test
    void contextLoadsTest() throws Exception {
        Assertions.assertThat(controller).isNotNull();
    }

    @Test
    void createAnimalTest() throws JsonProcessingException, JSONException {
        Animal animal = new Animal(3L, ANIMAL_NAME_1, ANIMAL_AGE_1, null);

        String expected = mapper.writeValueAsString(animal);

        ResponseEntity<String> response = restTemplate.postForEntity("/animal", animal, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void editAnimalTest() throws Exception {
        Animal animal = new Animal(ANIMAL_ID_1, ANIMAL_NAME_1, ANIMAL_AGE_1, null);

        HttpEntity<Animal> entity = new HttpEntity<>(animal);
        long animalId = savedAnimals.get(0).getId();

        ResponseEntity<Animal> response = restTemplate.exchange("/animal/" + animalId, HttpMethod.PUT, entity, Animal.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(animal, response.getBody());
    }

    @Test
    void getAnimalTest() throws JsonProcessingException, JSONException {
        String expected = mapper.writeValueAsString(savedAnimals.get(0));

        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:" + port + "/animal/" + savedAnimals.get(0).getId(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void deleteAnimalTest() throws Exception {
        int animalId = 1;

        this.restTemplate.delete("http://localhost:" + port + "/animal/" + animalId);

        ResponseEntity<Animal> responseEntity = this.restTemplate.getForEntity
                ("http://localhost:" + port + "/animal/" + animalId, Animal.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
