package pro.sky.telegrambot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String photo;
    private String food;
    private String health;
    private String changes;
    private boolean approval;

    public Report(Long reportId, String photo, String food, String health, String changes, boolean approval) {
        this.reportId = reportId;
        this.photo = photo;
        this.food = food;
        this.health = health;
        this.changes = changes;
        this.approval = approval;
    }

    public Report() {
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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
        return approval == report.approval && Objects.equals(reportId, report.reportId) && Objects.equals(photo, report.photo) && Objects.equals(food, report.food) && Objects.equals(health, report.health) && Objects.equals(changes, report.changes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, photo, food, health, changes, approval);
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", photo='" + photo + '\'' +
                ", food='" + food + '\'' +
                ", health='" + health + '\'' +
                ", changes='" + changes + '\'' +
                ", approval=" + approval +
                '}';
    }
}
