package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.AnimalAvatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<AnimalAvatar, Long> {


    Optional<AnimalAvatar> findByAnimalAvatarId(Long id);
}
