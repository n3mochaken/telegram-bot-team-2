package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.entity.Owner;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByChatId(long id);

    @Query("SELECT o.chatId FROM Owner o WHERE o.id = :id")
    Long findChatIdById(@Param("id") Long id);
}
