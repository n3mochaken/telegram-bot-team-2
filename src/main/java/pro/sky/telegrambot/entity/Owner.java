package pro.sky.telegrambot.entity;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Schema(description = "Сущность Усыновителя")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(0)
    @Schema(description = "ID", example = "0")
    private Long id;

    @Schema(description = "Персональный ID")
    private Long chatId;

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Schema(description = "Номер телефона", example = "+79112223456")
    private String phoneNumber;

    private boolean isOwner;

    @Schema(description = "Имя-Фамилия", example = "Вячеслав Мавродий")
    private String fullName;

    public Owner(Long id, Long chatId, Animal animal, String phoneNumber, boolean isOwner, String fullName) {
        this.id = id;
        this.chatId = chatId;
        this.animal = animal;
        this.phoneNumber = phoneNumber;
        this.isOwner = isOwner;
        this.fullName = fullName;
    }

    public Owner() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return isOwner == owner.isOwner && Objects.equals(id, owner.id) && Objects.equals(chatId, owner.chatId) && Objects.equals(animal, owner.animal) && Objects.equals(phoneNumber, owner.phoneNumber) && Objects.equals(fullName, owner.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, animal, phoneNumber, isOwner, fullName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
