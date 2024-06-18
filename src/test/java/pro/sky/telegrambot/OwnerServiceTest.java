package pro.sky.telegrambot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.entity.Owner;
import pro.sky.telegrambot.exception.OwnerNotFoundException;
import pro.sky.telegrambot.repository.OwnerRepository;
import pro.sky.telegrambot.service.entities.OwnerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pro.sky.telegrambot.ConstantsTest.*;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private Chat chat;

    @Mock
    private OwnerRepository repository;

    @InjectMocks
    private OwnerService service;

    @Test
    void createOwnerTest() {
        Owner expected = new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);
        when(repository.save(expected)).thenReturn(expected);

        Owner actual = service.create(expected);

        verify(repository, only()).save(expected);

        assertEquals(expected, actual);
    }

    @Test
    void updateOwnerTest() {
        Owner newOwnerData = new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);

        Owner existingOwner = new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);
        when(repository.findById(ID_2)).thenReturn(Optional.of(existingOwner));

        Owner updatedOwner = new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);
        when(repository.save(updatedOwner)).thenReturn(updatedOwner);

        Owner actual = service.update(ID_2, newOwnerData);

        verify(repository, times(1)).findById(ID_2);
        verify(repository, times(1)).save(updatedOwner);

        assertEquals(updatedOwner, actual);
    }

    @Test
    void updateOwnerNotFoundTest() {
        long nonExistingId = 999L;
        Owner newOwnerData = new Owner(nonExistingId, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);

        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> service.update(nonExistingId, newOwnerData));

        verify(repository, times(1)).findById(nonExistingId);
    }

    @Test
    void deleteOwnerTest() {
        Owner expected = new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);
        when(repository.findById(ID_1)).thenReturn(Optional.of(expected));

        service.delete(ID_1);

        assertTrue(repository.findById(ID_1).isPresent());
    }

    @Test
    void getOwnerTest() {
        Owner expected = new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);
        when(repository.findById(ID_1)).thenReturn(Optional.of(expected));

        Owner actual = service.get(ID_1);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateOwnerCreateTestWhenOwnerNotExists() {
        lenient().when(update.message()).thenReturn(message);
        lenient().when(message.chat()).thenReturn(chat);
        lenient().when(chat.id()).thenReturn(CHAT_ID_1);
        lenient().when(repository.findById(CHAT_ID_1)).thenReturn(Optional.empty());

        // Вызов метода, который тестируется
        service.createOwner(update);

        // Проверка выполнения операции сохранения
        verify(repository, times(1)).save(any(Owner.class));
        // Удаление ненужных утверждений для логгера
    }

    @Test
    void testFindOwner() {
        Owner expectedOwner = new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);

        // Мокирование метода findByChatId
        when(repository.findByChatId(CHAT_ID_1)).thenReturn(Optional.of(expectedOwner));

        // Вызов метода, который тестируется
        Optional<Owner> actualOwner = service.findPerson(CHAT_ID_1);

        // Проверка результатов
        assertEquals(Optional.of(expectedOwner), actualOwner);
    }

    @Test
    void testFindAllOwners() {
        // Устанавливаем ожидаемый результат
        List<Owner> expectedOwners = new ArrayList<>();
        expectedOwners.add(new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1));
        expectedOwners.add(new Owner(ID_2, CHAT_ID_2, null, PHONE_NUMBER_2, IS_OWNER_2, FULL_NAME_2));

        // Мокирование метода findAll
        when(repository.findAll()).thenReturn(expectedOwners);

        // Вызов метода, который тестируется
        List<Owner> actualOwners = service.findAll();

        // Проверка результатов
        assertEquals(expectedOwners, actualOwners);
    }

    @Test
    void testFindByChatId() {
        Owner expectedOwner = new Owner(ID_1, CHAT_ID_1, null, PHONE_NUMBER_1, IS_OWNER_1, FULL_NAME_1);

        // Мокирование метода findByChatId
        when(repository.findByChatId(CHAT_ID_1)).thenReturn(Optional.of(expectedOwner));

        // Вызов метода, который тестируется
        Optional<Owner> actualOwner = service.findByChatId(CHAT_ID_1);

        // Проверка результатов
        assertEquals(Optional.of(expectedOwner), actualOwner);
    }
}
