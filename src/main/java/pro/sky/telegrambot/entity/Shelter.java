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
    private String ADDRESS = "ADDRESS";
    private String CONTACTS = "CONTACTS";
    private String RULES = "RULES";
    @OneToMany(mappedBy = "shelter")
    private Collection<Animal> animals;

    public Shelter(Long shelterID, String INFO, String NAME, String ADDRESS, String CONTACTS, String RULES) {
        this.shelterID = shelterID;
        this.INFO = INFO;
        this.NAME = NAME;
        this.ADDRESS = ADDRESS;
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

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
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
        return Objects.equals(shelterID, shelter.shelterID) && Objects.equals(INFO, shelter.INFO) && Objects.equals(NAME, shelter.NAME) && Objects.equals(ADDRESS, shelter.ADDRESS) && Objects.equals(CONTACTS, shelter.CONTACTS) && Objects.equals(RULES, shelter.RULES);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelterID, INFO, NAME, ADDRESS, CONTACTS, RULES);
    }

    public Collection<Animal> getAnimals() {
        return animals;
    }


}
