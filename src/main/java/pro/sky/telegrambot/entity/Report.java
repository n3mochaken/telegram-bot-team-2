package pro.sky.telegrambot.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long report_Id;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "photo")
    private byte[] photo;
    private String food;
    private String health;
    private String changes;
    private boolean approval;

    @ManyToOne
    @JoinColumn(name="Owner_id")
    private Owner owner;

    // Конструкторы, геттеры, сеттеры, equals, hashCode, toString

    public Report(Long report_Id, byte[] photo, String food, String health, String changes, boolean approval) {
        this.report_Id = report_Id;
        this.photo = photo;
        this.food = food;
        this.health = health;
        this.changes = changes;
        this.approval = approval;
    }

    public Report() {
    }

    public Long getReportId() {
        return report_Id;
    }

    public void setReportId(Long report_Id) {
        this.report_Id = report_Id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return approval == report.approval &&
                Objects.equals(report_Id, report.report_Id) &&
                Arrays.equals(photo, report.photo) &&
                Objects.equals(food, report.food) &&
                Objects.equals(health, report.health) &&
                Objects.equals(changes, report.changes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(report_Id, food, health, changes, approval);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "report_Id=" + report_Id +
                ", photo=" + Arrays.toString(photo) +
                ", food='" + food + '\'' +
                ", health='" + health + '\'' +
                ", changes='" + changes + '\'' +
                ", approval=" + approval +
                '}';
    }

    public Owner getOwner(){
        return owner;
    }
}
