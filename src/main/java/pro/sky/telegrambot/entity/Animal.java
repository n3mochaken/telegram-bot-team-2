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
    //private String photoPass;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;




    public Animal(Long animalId, String animalName, int animalAge) {
        this.animalId = animalId;
        this.animalName = animalName;
        this.animalAge = animalAge;
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

    public Shelter getShelter (Shelter shelter){
        return shelter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return animalAge == animal.animalAge && Objects.equals(animalId, animal.animalId) && Objects.equals(animalName, animal.animalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, animalName, animalAge);
    }
}
