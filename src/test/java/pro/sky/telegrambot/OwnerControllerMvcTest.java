package pro.sky.telegrambot;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pro.sky.telegrambot.controllers.OwnerController;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.entity.Owner;
import pro.sky.telegrambot.repository.OwnerRepository;
import pro.sky.telegrambot.service.entities.OwnerService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
public class OwnerControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository repository;

    @SpyBean
    private OwnerService service;

    @InjectMocks
    private OwnerController controller;

    @Test
    void createOwnerTest() throws Exception {
       final Long id = 1L;
       final Long chatId = 5235125L;
       final Animal animal = null;
       final String phoneNumber = "+79112223456";
       final boolean owner = true;
       final String fullName = "Вячеслав Мавродий";

        Owner model = new Owner(id, chatId, animal, phoneNumber, owner, fullName);

        JSONObject modelObject = new JSONObject();
        modelObject.put("id", id);
        modelObject.put("chatId", chatId);
        modelObject.put("animal", animal);
        modelObject.put("phoneNumber", phoneNumber);
        modelObject.put("owner", owner);
        modelObject.put("fullName", fullName);

        when(repository.save(any(Owner.class))).thenReturn(model);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/owner/")
                        .content(modelObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.chatId").value(chatId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.animal").value(animal))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(phoneNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(fullName));
    }

    @Test
    void getOwnerTest() throws Exception {
        final Long id = 1L;
        final Long chatId = 5235125L;
        final Animal animal = null;
        final String phoneNumber = "+79112223456";
        final boolean owner = true;
        final String fullName = "Вячеслав Мавродий";

        Owner model = new Owner(id, chatId, animal, phoneNumber, owner, fullName);

        JSONObject modelObject = new JSONObject();
        modelObject.put("id", id);
        modelObject.put("chatId", chatId);
        modelObject.put("animal", animal);
        modelObject.put("phoneNumber", phoneNumber);
        modelObject.put("owner", owner);
        modelObject.put("fullName", fullName);

        when(repository.findById(id)).thenReturn(Optional.of(model));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/owner/" + id)
                        .content(modelObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.chatId").value(chatId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.animal").value(animal))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(phoneNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(fullName));
    }

    @Test
    void updateOwnerTest() throws Exception {
        final Long id = 1L;
        final Long chatId = 5235125L;
        final Animal animal = null;
        final String phoneNumber = "+79112223456";
        final String updatePhoneNumber = "+79212223456";
        final boolean owner = true;
        final String fullName = "Вячеслав Мавродий";

        Owner model = new Owner(id, chatId, animal, phoneNumber, owner, fullName);

        when(repository.findById(id)).thenReturn(Optional.ofNullable(model));
        when(repository.save(any(Owner.class))).thenReturn(model);


        JSONObject modelObjectForUpdate = new JSONObject();
        modelObjectForUpdate.put("id", id);
        modelObjectForUpdate.put("chatId", chatId);
        modelObjectForUpdate.put("animal", animal);
        modelObjectForUpdate.put("phoneNumber", updatePhoneNumber);
        modelObjectForUpdate.put("owner", owner);
        modelObjectForUpdate.put("fullName", fullName);

        assertTrue(repository.findById(id).isPresent());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/owner/" + id)
                        .content(modelObjectForUpdate.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.chatId").value(chatId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.animal").value(animal))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(updatePhoneNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(fullName));
    }

    @Test
    void deleteOwnerTest() throws Exception {
        final Long id = 1L;
        final Long chatId = 5235125L;
        final Animal animal = null;
        final String phoneNumber = "+79112223456";
        final boolean owner = true;
        final String fullName = "Вячеслав Мавродий";

        Owner model = new Owner(id, chatId, animal, phoneNumber, owner, fullName);
        when(repository.save(any(Owner.class))).thenReturn(model);
        when(repository.findById(id)).thenReturn(Optional.ofNullable(model));

        JSONObject modelObject = new JSONObject();
        modelObject.put("id", id);
        modelObject.put("chatId", chatId);
        modelObject.put("animal", animal);
        modelObject.put("phoneNumber", phoneNumber);
        modelObject.put("owner", owner);
        modelObject.put("fullName", fullName);

        assertTrue(repository.findById(id).isPresent());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/owner/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
