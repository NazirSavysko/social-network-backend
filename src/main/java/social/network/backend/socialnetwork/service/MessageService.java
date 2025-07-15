package social.network.backend.socialnetwork.service;

import social.network.backend.socialnetwork.entity.Message;

import java.util.List;

public interface MessageService {

    Message createMessage(Message message);

    Message getMessageById(Integer messageId);

    Message updateMessage(Message message);

    void deleteMessage(Integer messageId);

    List<Message> getAllMessagesByUserId(Integer userId);
}
