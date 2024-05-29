package pro.sky.telegrambot.entity.person;

import pro.sky.telegrambot.entity.Animal;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "owners")
public class Owner extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int reportsCounter;
    private int targetReportCount;

    @OneToMany
    @JoinColumn(name = "animal_id")
    private List<Animal> hasAnimal;


    public Owner() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReportsCounter() {
        return reportsCounter;
    }

    public void setReportsCounter(int reportsCounter) {
        this.reportsCounter = reportsCounter;
    }

    public int getTargetReportCount() {
        return targetReportCount;
    }

    public void setTargetReportCount(int targetReportCount) {
        this.targetReportCount = targetReportCount;
    }

    public List<Animal> getHasAnimal() {
        return hasAnimal;
    }

    public void setHasAnimal(List<Animal> hasAnimal) {
        this.hasAnimal = hasAnimal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Owner owner = (Owner) o;
        return reportsCounter == owner.reportsCounter && targetReportCount == owner.targetReportCount && Objects.equals(id, owner.id) && Objects.equals(hasAnimal, owner.hasAnimal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, reportsCounter, targetReportCount, hasAnimal);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", reportsCounter=" + reportsCounter +
                ", targetReportCount=" + targetReportCount +
                ", hasAnimal=" + hasAnimal +
                '}';
    }
}
