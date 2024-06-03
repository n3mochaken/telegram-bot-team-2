package pro.sky.telegrambot.entity;
import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;


@Entity
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelterID;
    private String INFO = "INFO";
    private String NAME = "NAME";
    private String ADRESS = "ADRESS";
    private String CONTACTS = "CONTACTS";
    private String RULES = "RULES";
    @OneToMany(mappedBy = "shelter")
    private Collection<Animal> animals;

    public Shelter(Long shelterID, String INFO, String NAME, String ADRESS, String CONTACTS, String RULES) {
        this.shelterID = shelterID;
        this.INFO = INFO;
        this.NAME = NAME;
        this.ADRESS = ADRESS;
        this.CONTACTS = CONTACTS;
        this.RULES = RULES;
    }

    public Shelter() {
    }

    public Long getShelterID() {
        return shelterID;
    }

    public void setShelterID(Long shelterID) {
        this.shelterID = shelterID;
    }

    public String getINFO() {
        return INFO;
    }

    public void setINFO(String INFO) {
        this.INFO = INFO;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getADRESS() {
        return ADRESS;
    }

    public void setADRESS(String ADRESS) {
        this.ADRESS = ADRESS;
    }

    public String getCONTACTS() {
        return CONTACTS;
    }

    public void setCONTACTS(String CONTACTS) {
        this.CONTACTS = CONTACTS;
    }

    public String getRULES() {
        return RULES;
    }

    public void setRULES(String RULES) {
        this.RULES = RULES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(shelterID, shelter.shelterID) && Objects.equals(INFO, shelter.INFO) && Objects.equals(NAME, shelter.NAME) && Objects.equals(ADRESS, shelter.ADRESS) && Objects.equals(CONTACTS, shelter.CONTACTS) && Objects.equals(RULES, shelter.RULES);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelterID, INFO, NAME, ADRESS, CONTACTS, RULES);
    }

    public Collection<Animal> getAnimals() {
        return animals;
    }


}
