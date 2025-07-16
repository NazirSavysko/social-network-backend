package social.network.backend.socialnetwork.service.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.service.MessageService;
import social.network.backend.socialnetwork.utils.EntityDtoFactory;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static social.network.backend.socialnetwork.utils.EntityDtoFactory.createListOfEntity;
import static social.network.backend.socialnetwork.utils.EntityDtoFactory.createMessagesEntity;

@Service
public final class MessageServiceImpl implements MessageService {

    @Contract("_ -> new")
    @Override
    public @NotNull Optional<Message> getMessageById(final Integer messageId) {

        return of(createMessagesEntity());
    }

    @Override
    public void deleteMessage(final Integer messageId) {

    }

    @Override
    public @NotNull @Unmodifiable List<Message> getAllMessagesByUserId(final Integer userId) {

        return createListOfEntity(EntityDtoFactory::createMessagesEntity);
    }

    @Override
    public Message createMessage(final String content, final Integer senderId, final Integer recipientId) {
        return createMessagesEntity();
    }

    @Override
    public Message updateMessage(final Integer id, final String content) {
        return createMessagesEntity();
    }
}
