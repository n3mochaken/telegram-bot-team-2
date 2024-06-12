package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
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
