package pro.sky.telegrambot.entity;


import pro.sky.telegrambot.entity.person.Owner;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Animal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return id == animal.id && Objects.equals(owner, animal.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", owner=" + owner +
                '}';
    }
}
