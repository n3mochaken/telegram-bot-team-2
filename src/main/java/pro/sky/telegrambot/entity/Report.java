package pro.sky.telegrambot.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "photo")
    private byte[] photo;
    private String food;
    private String health;
    private String changes;
    private boolean approval;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // Конструкторы, геттеры, сеттеры, equals, hashCode, toString


    public Report(Long id, byte[] photo, String food, String health, String changes, boolean approval, Owner owner) {
        this.id = id;
        this.photo = photo;
        this.food = food;
        this.health = health;
        this.changes = changes;
        this.approval = approval;
        this.owner = owner;
    }

    public Report() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
