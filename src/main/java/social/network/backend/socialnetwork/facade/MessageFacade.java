package social.network.backend.socialnetwork.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.message.CreateMessageDTO;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.message.UpdateMessageDTO;


public interface MessageFacade  {

    GetMessageDTO createMessage(CreateMessageDTO dtoForCreate, BindingResult result);

    GetMessageDTO updateMessage(UpdateMessageDTO dtoForUpdate, BindingResult result);

    GetMessageDTO getMessageById(Integer messageId);

    void deleteMessage(Integer messageId);

    Page<GetMessageDTO> getAllMessagesByUserId(Integer userId, final Pageable pageable);
}
