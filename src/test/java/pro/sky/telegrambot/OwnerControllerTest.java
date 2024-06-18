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
import pro.sky.telegrambot.controllers.OwnerController;
import pro.sky.telegrambot.entity.Owner;
import pro.sky.telegrambot.repository.OwnerRepository;
import pro.sky.telegrambot.service.entities.OwnerService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.sky.telegrambot.ConstantsTest.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerControllerTest {

    private List<Owner> savedOwners;
    private static final ObjectMapper mapper = new ObjectMapper();

    @LocalServerPort
    private int port;

    @Autowired
    private OwnerController controller;

    @Autowired
    private OwnerService service;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OwnerRepository repository;

    @BeforeEach
    public void setUp() {
        Owner owner1 = new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);
        Owner owner2 = new Owner(ID_2, CHAT_ID_2, null, PHONE_NUMBER_2, IS_OWNER_2, FULL_NAME_2);
        List<Owner> owners = List.of(owner1, owner2);

        savedOwners = repository.saveAll(owners);
    }

    @Test
    void contextLoadsTest() throws Exception {
        Assertions.assertThat(controller).isNotNull();
    }

    @Test
    void createOwnerTest() throws JsonProcessingException, JSONException {
        long id  = 3L;
        Owner owner = new Owner(id, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);

        String expected = mapper.writeValueAsString(owner);

        ResponseEntity<String> response = restTemplate.postForEntity("/owner" , owner, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void editOwnerTest() throws Exception {
        Owner owner = new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);

        HttpEntity<Owner> entity = new HttpEntity<>(owner);
        owner.setId(savedOwners.get(0).getId());

        ResponseEntity<Owner> response = restTemplate.exchange("/owner/", HttpMethod.PUT, entity, Owner.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(owner, response.getBody());
    }

    @Test
    void getOwnersTest() throws JsonProcessingException, JSONException {
        String expected = mapper.writeValueAsString(savedOwners.get(0));

        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:" + port + "/owner/" + savedOwners.get(0).getId(),
                        String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void deleteOwnerTest() throws Exception {
        int ownerId = 1;

        this.restTemplate.delete("http://localhost:" + port + "/owner/" + ownerId);

        ResponseEntity<Owner> responseEntity = this.restTemplate.getForEntity
                ("http://localhost:" + port + "/student/" + ownerId, Owner.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
