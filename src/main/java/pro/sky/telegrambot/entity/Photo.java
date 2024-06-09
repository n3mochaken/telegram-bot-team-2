package pro.sky.telegrambot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photo_id;
    private String filePath;
    private Long chatId;

    public Photo(String filePath, Long chatId) {
        this.filePath = filePath;
        this.chatId = chatId;
    }
    public Photo(){}

    public Long getId() {
        return photo_id;
    }

    public void setId(Long photo_id) {
        this.photo_id = photo_id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(photo_id, photo.photo_id) && Objects.equals(filePath, photo.filePath) && Objects.equals(chatId, photo.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photo_id, filePath, chatId);
    }
}
