package social.network.backend.socialnetwork.service;

import social.network.backend.socialnetwork.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {


    Optional<Message> getMessageById(Integer messageId);


    void deleteMessage(Integer messageId);

    List<Message> getAllMessagesByUserId(Integer userId);

    Message createMessage(String content, Integer senderId, Integer recipientId);

    Message updateMessage(Integer id, String content);
}
