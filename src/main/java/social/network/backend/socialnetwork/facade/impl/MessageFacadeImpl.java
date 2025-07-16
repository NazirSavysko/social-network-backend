package social.network.backend.socialnetwork.facade.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.message.CreateMessageDTO;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.message.UpdateMessageDTO;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.facade.MessageFacade;
import social.network.backend.socialnetwork.facade.mapper.Mapper;
import social.network.backend.socialnetwork.service.MessageService;
import social.network.backend.socialnetwork.validation.DtoValidator;

import java.util.List;
import java.util.NoSuchElementException;

import static social.network.backend.socialnetwork.validation.ValidationMessage.ERROR_MESSAGE_ID_MUST_BE_POSITIVE;
import static social.network.backend.socialnetwork.validation.ValidationMessage.ERROR_USER_ID_MUST_BE_POSITIVE;

@Component
public final class MessageFacadeImpl implements MessageFacade {

    private final MessageService messageService;
    private final Mapper<Message, GetMessageDTO> messageMapper;
    private final DtoValidator validator;

    @Autowired
    public MessageFacadeImpl(final MessageService messageService,
                             final Mapper<Message, GetMessageDTO> messageMapper,
                             final DtoValidator validator) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.validator = validator;
    }


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
        this.validateMessageId(messageId);

        final Message message = this.messageService.getMessageById(messageId)
                .orElseThrow(() ->
                        new NoSuchElementException("Message with ID " + messageId + " not found.")
                );

        return this.messageMapper.toDto(message);
    }

    @Override
    public void deleteMessage(final Integer messageId) {
        this.validateMessageId(messageId);

        this.messageService.deleteMessage(messageId);
    }

    @Contract(pure = true)
    @Override
    public @NotNull @Unmodifiable List<GetMessageDTO> getAllMessagesByUserId(final Integer userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException(ERROR_USER_ID_MUST_BE_POSITIVE);
        }

        return this.messageService.getAllMessagesByUserId(userId).stream()
                .map(this.messageMapper::toDto)
                .toList();
    }

    private void validateMessageId(final Integer messageId) {
        if (messageId == null || messageId <= 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE_ID_MUST_BE_POSITIVE);
        }
    }
}
