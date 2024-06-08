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
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Person(Long personId, Long chatId, String phoneNumber, Owner owner) {
        this.personId = personId;
        this.chatId = chatId;
        this.phoneNumber = phoneNumber;
        this.owner = owner;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personId, person.personId) && Objects.equals(chatId, person.chatId) && Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(owner, person.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, chatId, phoneNumber, owner);
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", chatId=" + chatId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", owner=" + owner +
                '}';
    }
}
