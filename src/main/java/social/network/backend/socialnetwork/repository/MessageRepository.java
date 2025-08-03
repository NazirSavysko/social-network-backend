package social.network.backend.socialnetwork.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import social.network.backend.socialnetwork.entity.Message;


public interface MessageRepository extends JpaRepository<Message, Integer> {
    Page<Message> findAllBySender_Id(Integer senderId, Pageable pageable);
}
