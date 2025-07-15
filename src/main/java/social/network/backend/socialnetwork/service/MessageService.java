package social.network.backend.socialnetwork.service;

import social.network.backend.socialnetwork.entity.Message;

import java.util.List;

public interface MessageService {


    Message getMessageById(Integer messageId);


    void deleteMessage(Integer messageId);

    List<Message> getAllMessagesByUserId(Integer userId);

    Message createMessage(String content, Integer senderId, Integer recipientId);

    Message updateMessage(Integer id, String content);
}
