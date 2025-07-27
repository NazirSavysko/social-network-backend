package social.network.backend.socialnetwork.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.data.jpa.repository.JpaRepository;
import social.network.backend.socialnetwork.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @NotNull
    @Unmodifiable List<Message> findAllBySender_Id(Integer senderId);
}
