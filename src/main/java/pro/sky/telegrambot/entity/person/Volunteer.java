package pro.sky.telegrambot.entity.person;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "volunteers")
public class Volunteer extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isHelper;


    public Volunteer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isHelper() {
        return isHelper;
    }

    public void setHelper(boolean helper) {
        isHelper = helper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Volunteer volunteer = (Volunteer) o;
        return isHelper == volunteer.isHelper && Objects.equals(id, volunteer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, isHelper);
    }

    @Override
    public String toString() {
        return "Volunteer" + id + ", isHelper=" + isHelper;
    }
}
