package pro.sky.telegrambot.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Schema(description = "Сущность Животного")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(0)
    @Schema(description = "ID", example = "0")
    private Long id;

    @Schema(description = "Имя животного")
    private String name;

    @Schema(description = "Возраст")
    private int age;

    @Schema(description = "Фото животного")
    private String photoPass;

    public Animal(Long id, String name, int age, String photoPass) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.photoPass = photoPass;
    }

    public Animal() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhotoPass() {
        return photoPass;
    }

    public void setPhotoPass(String photoPass) {
        this.photoPass = photoPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age && Objects.equals(id, animal.id) && Objects.equals(name, animal.name) && Objects.equals(photoPass, animal.photoPass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, photoPass);
    }
}
