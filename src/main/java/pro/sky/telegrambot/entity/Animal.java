package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;

    private String animalName;
    private int animalAge;
    private String photoPass;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;


    public Animal(Long animalId, String animalName, int animalAge, String photoPass, Owner owner) {
        this.animalId = animalId;
        this.animalName = animalName;
        this.animalAge = animalAge;
        this.photoPass = photoPass;
        this.owner = owner;
    }

    public Animal() {
    }

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public int getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(int animalAge) {
        this.animalAge = animalAge;
    }

    public String getPhotoPass() {
        return photoPass;
    }

    public void setPhotoPass(String photoPass) {
        this.photoPass = photoPass;
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
        Animal animal = (Animal) o;
        return animalAge == animal.animalAge && Objects.equals(animalId, animal.animalId) && Objects.equals(animalName, animal.animalName) && Objects.equals(photoPass, animal.photoPass) && Objects.equals(owner, animal.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, animalName, animalAge, photoPass, owner);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalId=" + animalId +
                ", animalName='" + animalName + '\'' +
                ", animalAge=" + animalAge +
                ", photoPass='" + photoPass + '\'' +
                ", owner=" + owner +
                '}';
    }
}
