package social.network.backend.socialnetwork.service;

import social.network.backend.socialnetwork.entity.Message;

import java.util.List;

public interface MessageService {
    /**
     * Creates a new message.
     *
     * @param message the message to create
     * @return the created message
     */
    Message createMessage(Message message);

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId the ID of the message to retrieve
     * @return the retrieved message
     */
    Message getMessageById(Integer messageId);

    /**
     * Updates an existing message.
     *
     * @param message the message to update
     * @return the updated message
     */
    Message updateMessage(Message message);

    /**
     * Deletes a message by its ID.
     *
     * @param messageId the ID of the message to delete
     */
    void deleteMessage(Integer messageId);

    /**
     * Retrieves all messages sent by a user.
     *
     * @param userId the ID of the user whose messages are to be retrieved
     * @return a list of messages sent by the user
     */
    List<Message> getAllMessagesByUserId(Integer userId);
}
