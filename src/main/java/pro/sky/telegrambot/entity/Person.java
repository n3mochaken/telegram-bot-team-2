package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    private Long chatId;
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public Person(Long personId, Long chatId, String phoneNumber) {
        this.personId = personId;
        this.chatId = chatId;
        this.phoneNumber = phoneNumber;
    }

    public Person() {
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personId, person.personId) && Objects.equals(chatId, person.chatId) && Objects.equals(phoneNumber, person.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, chatId, phoneNumber);
    }

    @Override
    public String toString() {
        return "Person:" + personId + ", chatId=" + chatId + ", phoneNumber='" + phoneNumber;
    }
}
