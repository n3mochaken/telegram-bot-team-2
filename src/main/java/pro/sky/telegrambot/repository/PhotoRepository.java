package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
}
