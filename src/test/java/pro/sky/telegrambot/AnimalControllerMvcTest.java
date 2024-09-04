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
import pro.sky.telegrambot.controllers.AnimalController;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.repository.AnimalRepository;
import pro.sky.telegrambot.service.entities.AnimalService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnimalController.class)
public class AnimalControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalRepository repository;

    @SpyBean
    private AnimalService service;

    @InjectMocks
    private AnimalController controller;

    @Test
    void createAnimalTest() throws Exception {
        final Long id = 1L;
        final String name = "Зевс";
        final int age = 1;
        final String photoPass = null;

        Animal model = new Animal(id, name, age, photoPass);

        JSONObject modelObject = new JSONObject();
        modelObject.put("id", id);
        modelObject.put("name", name);
        modelObject.put("age", age);
        modelObject.put("photoPass", photoPass);

        when(repository.save(any(Animal.class))).thenReturn(model);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/animal")
                .content(modelObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age))
                .andExpect(MockMvcResultMatchers.jsonPath("$.photoPass").value(photoPass));
    }

    @Test
    void getAnimalTest() throws Exception {
        final Long id = 1L;
        final String name = "Зевс";
        final int age = 1;
        final String photoPass = null;

        Animal model = new Animal(id, name, age, photoPass);

        JSONObject modelObject = new JSONObject();
        modelObject.put("id", id);
        modelObject.put("name", name);
        modelObject.put("age", age);
        modelObject.put("photoPass", photoPass);

        when(repository.findById(any(Long.class))).thenReturn(Optional.of(model));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/animal/" + id)
                .content(modelObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age))
                .andExpect(MockMvcResultMatchers.jsonPath("$.photoPass").value(photoPass));
    }

    @Test
    void updateAnimalTest() throws Exception {
        final Long id = 1L;
        final String name = "Зевс";
        final String updateName = "Зс";
        final int age = 1;
        final String photoPass = null;

        Animal model = new Animal(id, name, age, photoPass);
        when(repository.save(any(Animal.class))).thenReturn(model);
        when(repository.findById(id)).thenReturn(Optional.ofNullable(model));

        JSONObject modelObjectForUpdate = new JSONObject();
        modelObjectForUpdate.put("id", id);
        modelObjectForUpdate.put("name", updateName);
        modelObjectForUpdate.put("age", age);
        modelObjectForUpdate.put("photoPass", photoPass);

        assertTrue(repository.findById(id).isPresent());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/animal/" + id)
                        .content(modelObjectForUpdate.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updateName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age))
                .andExpect(MockMvcResultMatchers.jsonPath("$.photoPass").value(photoPass));
    }

    @Test
    void deleteAnimalTest() throws Exception {
        final Long id = 1L;
        final String name = "Зевс";
        final int age = 1;
        final String photoPass = null;

        Animal model = new Animal(id, name, age, photoPass);
        when(repository.save(any(Animal.class))).thenReturn(model);
        when(repository.findById(id)).thenReturn(Optional.ofNullable(model));

        JSONObject modelObject = new JSONObject();
        modelObject.put("id", id);
        modelObject.put("name", name);
        modelObject.put("age", age);
        modelObject.put("photoPass", photoPass);

        assertTrue(repository.findById(id).isPresent());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/animal    /" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
