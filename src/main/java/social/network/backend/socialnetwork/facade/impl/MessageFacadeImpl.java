package social.network.backend.socialnetwork.facade.impl;

import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.message.CreateMessageDTO;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.message.UpdateMessageDTO;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.facade.MessageFacade;
import social.network.backend.socialnetwork.facade.mapper.Mapper;
import social.network.backend.socialnetwork.facade.mapper.impl.MessageMapperImpl;
import social.network.backend.socialnetwork.service.MessageService;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public final class MessageFacadeImpl implements MessageFacade {

    private final MessageService messageService;
    private final Mapper<Message, UpdateMessageDTO, CreateMessageDTO, GetMessageDTO> messageMapper;

    @Autowired
    public MessageFacadeImpl(final MessageService messageService,
                             @Qualifier("messageMapper") final MessageMapperImpl messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }


    @Override
    public GetMessageDTO createMessage(@Valid final CreateMessageDTO createMessageDTO, @NotNull final BindingResult result) {
        if (result.hasErrors()) {

            throw new IllegalArgumentException(
                    "Validation errors occurred: " + result.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                            .orElse("Unknown error")
            );
        } else {
            final Message savedMessage = this.messageService.createMessage(createMessageDTO.content(), createMessageDTO.senderId(), createMessageDTO.receiverId());

            return this.messageMapper.toDto(savedMessage);
        }
    }

    @Override
    public GetMessageDTO updateMessage(@Valid final UpdateMessageDTO dtoForUpdate, @NotNull final BindingResult result) {
        if (result.hasErrors()) {

            throw new IllegalArgumentException(
                    "Validation errors occurred: " + result.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                            .orElse("Unknown error")
            );
        } else {
            final Message updatedMessage = this.messageService.updateMessage(dtoForUpdate.id(), dtoForUpdate.content());

            return this.messageMapper.toDto(updatedMessage);
        }
    }

    @Override
    public GetMessageDTO getMessageById(final Integer messageId) {
        if (messageId == null || messageId <= 0) {

            throw new IllegalArgumentException("Message ID must be a positive integer.");
        } else {
            final Message message = this.messageService.getMessageById(messageId);

            if (message == null) {
                throw new NoSuchElementException("Message with ID " + messageId + " not found.");
            }

            return this.messageMapper.toDto(message);
        }
    }

    @Override
    public void deleteMessage(final Integer messageId) {
        if (messageId == null || messageId <= 0) {
            throw new IllegalArgumentException("Message ID must be a positive integer.");
        }

        this.messageService.deleteMessage(messageId);
    }

    @Contract(pure = true)
    @Override
    public @NotNull @Unmodifiable List<GetMessageDTO> getAllMessagesByUserId(final Integer userId) {

        return this.messageService.getAllMessagesByUserId(userId).stream()
                .map(this.messageMapper::toDto)
                .toList();
    }
}
