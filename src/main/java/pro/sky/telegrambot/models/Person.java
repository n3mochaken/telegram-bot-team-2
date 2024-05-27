package pro.sky.telegrambot.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

public class Person {

    @Id
    @GeneratedValue
    private Long personId;

    private Long chatId;
    private String phoneNumber;
    private boolean hasAnimal;
    private int reportCounter;
    private int reportsRequired;

    public Person(Long personId, Long chatId, String phoneNumber, boolean hasAnimal,
                  int reportCounter, int reportsRequired) {
        this.personId = personId;
        this.chatId = chatId;
        this.phoneNumber = phoneNumber;
        this.hasAnimal = hasAnimal;
        this.reportCounter = reportCounter;
        this.reportsRequired = reportsRequired;
    }

    public Person() {
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isHasAnimal() {
        return hasAnimal;
    }

    public void setHasAnimal(boolean hasAnimal) {
        this.hasAnimal = hasAnimal;
    }

    public int getReportCounter() {
        return reportCounter;
    }

    public void setReportCounter(int reportCounter) {
        this.reportCounter = reportCounter;
    }

    public int getReportsRequired() {
        return reportsRequired;
    }

    public void setReportsRequired(int reportsRequired) {
        this.reportsRequired = reportsRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return hasAnimal == person.hasAnimal
                && reportCounter == person.reportCounter
                && reportsRequired == person.reportsRequired
                && Objects.equals(personId, person.personId)
                && Objects.equals(chatId, person.chatId)
                && Objects.equals(phoneNumber, person.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, chatId, phoneNumber, hasAnimal, reportCounter, reportsRequired);
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", chatId=" + chatId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hasAnimal=" + hasAnimal +
                ", reportCounter=" + reportCounter +
                ", reportsRequired=" + reportsRequired +
                '}';
    }
}
