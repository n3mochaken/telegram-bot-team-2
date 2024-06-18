package pro.sky.telegrambot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.exception.AnimalNotFoundException;
import pro.sky.telegrambot.repository.AnimalRepository;
import pro.sky.telegrambot.service.entities.AnimalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pro.sky.telegrambot.ConstantsTest.*;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository repository;

    @InjectMocks
    private AnimalService service;


    @Test
    void animalCreateTest() {
        Animal expected = new Animal(ANIMAL_ID_1, ANIMAL_NAME_1, ANIMAL_AGE_1, null);
        when(repository.save(expected)).thenReturn(expected);

        Animal actual = service.create(expected);

        verify(repository, only()).save(expected);

        assertEquals(expected, actual);
    }

    @Test
    void updateAnimalTest() {
        Animal newAnimalData = new Animal(ANIMAL_ID_1, ANIMAL_NAME_1, ANIMAL_AGE_1, null);

        Animal existingAnimal = new Animal(ANIMAL_ID_1, ANIMAL_NAME_1, ANIMAL_AGE_1, null);
        when(repository.findById(ANIMAL_ID_2)).thenReturn(Optional.of(existingAnimal));

        Animal updateAnimal = new Animal(ANIMAL_ID_1, ANIMAL_NAME_1, ANIMAL_AGE_1, null);
        when(repository.save(updateAnimal)).thenReturn(updateAnimal);

        Animal actual = service.update(ANIMAL_ID_2, newAnimalData);

        verify(repository, times(1)).findById(ANIMAL_ID_2);
        verify(repository, times(1)).save(updateAnimal);

        assertEquals(updateAnimal, actual);
    }

    @Test
    void updateAnimalNotFoundTest() {
        long nonExistingId = 999L;
        Animal newAnimalData = new Animal(nonExistingId, ANIMAL_NAME_1, ANIMAL_AGE_1, null);

        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(AnimalNotFoundException.class, () -> service.update(nonExistingId, newAnimalData));

        verify(repository, times(1)).findById(nonExistingId);
    }

    @Test
    void deleteAnimalTest() {
        Animal expected = new Animal(ANIMAL_ID_1, ANIMAL_NAME_1, ANIMAL_AGE_1, null);
        when(repository.findById(ANIMAL_ID_1)).thenReturn(Optional.of(expected));

        service.delete(ANIMAL_ID_1);

        assertTrue(repository.findById(ANIMAL_ID_1).isPresent());
    }

    @Test
    void getAnimalTest() {
        Animal expected = new Animal(ANIMAL_ID_1, ANIMAL_NAME_1, ANIMAL_AGE_1, null);
        when(repository.findById(ANIMAL_ID_1)).thenReturn(Optional.of(expected));

        Animal actual = service.get(ANIMAL_ID_1);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testFindAllAnimals() {
        List<Animal> expectedAnimals = new ArrayList<>();
        expectedAnimals.add(new Animal(ANIMAL_ID_1, ANIMAL_NAME_1, ANIMAL_AGE_1, null));
        expectedAnimals.add(new Animal(ANIMAL_ID_2, ANIMAL_NAME_2, ANIMAL_AGE_2, null));

        when(repository.findAll()).thenReturn(expectedAnimals);

        List<Animal> actualAnimals = service.findAll();

        assertEquals(expectedAnimals, actualAnimals);
    }
}
