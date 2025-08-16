package social.network.backend.socialnetwork.facade.impl;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.message.CreateMessageDTO;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.message.UpdateMessageDTO;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.facade.MessageFacade;
import social.network.backend.socialnetwork.mapper.Mapper;
import social.network.backend.socialnetwork.service.MessageService;
import social.network.backend.socialnetwork.validation.DtoValidator;

@Component
@AllArgsConstructor
public final class MessageFacadeImpl implements MessageFacade {

    private final MessageService messageService;
    private final Mapper<Message, GetMessageDTO> messageMapper;
    private final DtoValidator validator;

    @Override
    public GetMessageDTO createMessage(final @NotNull CreateMessageDTO createMessageDTO,
                                       final @NotNull BindingResult result) {
        validator.validate(createMessageDTO, result);

        final Message savedMessage = this.messageService.createMessage(
                createMessageDTO.content(),
                createMessageDTO.senderId(),
                createMessageDTO.receiverId()
        );

        return this.messageMapper.toDto(savedMessage);
    }

    @Override
    public GetMessageDTO updateMessage(final @NotNull UpdateMessageDTO dtoForUpdate,
                                       final @NotNull BindingResult result) {
        validator.validate(dtoForUpdate, result);

        final Message updatedMessage = this.messageService.updateMessage(
                dtoForUpdate.id(),
                dtoForUpdate.content()
        );

        return this.messageMapper.toDto(updatedMessage);
    }

    @Override
    public GetMessageDTO getMessageById(final Integer messageId) {
        final Message message = this.messageService.getMessageById(messageId);

        return this.messageMapper.toDto(message);
    }

    @Override
    public void deleteMessage(final Integer messageId) {
        this.messageService.deleteMessage(messageId);
    }

    @Contract(pure = true)
    @Override
    public @NotNull Page<GetMessageDTO> getAllMessagesByUserId(final Integer userId, final Pageable pageable) {
        final Page<Message> allMessagesByUserId = this.messageService.getAllMessagesByUserId(userId, pageable);

        return allMessagesByUserId.map(this.messageMapper::toDto);
    }
}
