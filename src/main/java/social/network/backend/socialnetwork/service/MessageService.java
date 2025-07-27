package social.network.backend.socialnetwork.service;

import org.springframework.transaction.annotation.Transactional;
import social.network.backend.socialnetwork.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    @Transactional(readOnly = true)
    Optional<Message> getMessageById(Integer messageId);

    @Transactional(rollbackFor = Exception.class)
    void deleteMessage(Integer messageId);

    @Transactional(readOnly = true)
    List<Message> getAllMessagesByUserId(Integer userId);

    @Transactional(rollbackFor = Exception.class)
    Message createMessage(String content, Integer senderId, Integer recipientId);

    @Transactional(rollbackFor = Exception.class)
    Message updateMessage(Integer id, String content);
}
