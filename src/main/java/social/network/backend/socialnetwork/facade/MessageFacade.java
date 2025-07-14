package social.network.backend.socialnetwork.facade;

import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.message.CreateMessageDTO;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.message.UpdateMessageDTO;

import java.util.List;

public interface MessageFacade  {

    /**
     * Creates a new message.
     *
     * @param dtoForCreate the DTO containing the message data to create
     * @param result       the binding result for validation errors
     * @return the created message as a DTO
     */
    GetMessageDTO createMessage(CreateMessageDTO dtoForCreate, BindingResult result);

    /**
     * Updates an existing message.
     *
     * @param dtoForUpdate the DTO containing the updated message data
     * @param result       the binding result for validation errors
     * @return the updated message as a DTO
     */
    GetMessageDTO updateMessage(UpdateMessageDTO dtoForUpdate, BindingResult result);

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId the ID of the message to retrieve
     * @return the retrieved message as a DTO
     */
    GetMessageDTO getMessageById(Integer messageId);

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
    List<GetMessageDTO> getAllMessagesByUserId(Integer userId);
}
