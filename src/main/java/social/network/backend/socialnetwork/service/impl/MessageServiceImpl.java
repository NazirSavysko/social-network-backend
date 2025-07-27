package social.network.backend.socialnetwork.service.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.repository.MessageRepository;
import social.network.backend.socialnetwork.service.MessageService;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull Optional<Message> getMessageById(final Integer messageId) {

        return this.messageRepository.findById(messageId);
    }

    @Override
    public void deleteMessage(final Integer messageId) {
        this.messageRepository.deleteById(messageId);
    }

    @Override
    public @NotNull @Unmodifiable List<Message> getAllMessagesByUserId(final Integer userId) {

        return this.messageRepository.findAllBySender_Id(userId);
    }

    @Override
    public @NotNull Message createMessage(final String content, final Integer senderId, final Integer recipientId) {
        final Message message = Message.builder()
                .messageText(content)
                .sender(User.builder().id(senderId).build())
                .recipient(User.builder().id(recipientId).build())
                .build();

        return this.messageRepository.save(message);
    }

    @Override
    public @NotNull Message updateMessage(final Integer id, final String content) {
        final Message message = this.messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        message.setMessageText(content);
        return this.messageRepository.save(message);
    }
}
