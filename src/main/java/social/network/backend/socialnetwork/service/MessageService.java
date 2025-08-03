package social.network.backend.socialnetwork.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import social.network.backend.socialnetwork.entity.Message;

public interface MessageService {

    @Transactional(readOnly = true)
    Message getMessageById(Integer messageId);

    @Transactional(rollbackFor = Exception.class)
    void deleteMessage(Integer messageId);

    @Transactional(readOnly = true)
    Page<Message> getAllMessagesByUserId(Integer userId, final Pageable pageable);

    @Transactional(rollbackFor = Exception.class)
    Message createMessage(String content, Integer senderId, Integer recipientId);

    @Transactional(rollbackFor = Exception.class)
    Message updateMessage(Integer id, String content);
}
