package pro.sky.telegrambot.entity;


import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerId;

    @OneToOne
    @JoinColumn(name = "chat_id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @OneToMany
    @JoinColumn(name = "report_id")
    private Collection<Report> reports;

    private int countApprovedReport;

    public Owner(Long ownerId, Person person, Animal animal, int countApprovedReport) {
        this.ownerId = ownerId;
        this.person = person;
        this.animal = animal;

        this.countApprovedReport = countApprovedReport;
    }

    public Owner() {
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }


    public int getCountApprovedReport() {
        return countApprovedReport;
    }

    public void setCountApprovedReport(int countApprovedReport) {
        this.countApprovedReport = countApprovedReport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return countApprovedReport == owner.countApprovedReport && Objects.equals(ownerId, owner.ownerId) && Objects.equals(person, owner.person) && Objects.equals(animal, owner.animal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, person, animal,countApprovedReport);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "ownerId=" + ownerId +
                ", person=" + person +
                ", animal=" + animal +
                ", countApprovedReport=" + countApprovedReport +
                '}';
    }

    public Collection<Report> getReport(){
        return reports;
    }
}
