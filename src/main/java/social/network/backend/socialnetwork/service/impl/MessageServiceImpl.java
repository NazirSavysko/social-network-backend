package social.network.backend.socialnetwork.service.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.repository.MessageRepository;
import social.network.backend.socialnetwork.service.MessageService;
import social.network.backend.socialnetwork.service.UserService;

import java.util.NoSuchElementException;

import static java.time.LocalDateTime.now;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    @Autowired
    public MessageServiceImpl(final MessageRepository messageRepository, final UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Contract("_ -> new")
    @Override
    public Message getMessageById(final Integer messageId) {
        return this.getMessageOrTrow(messageId);
    }

    @Override
    public void deleteMessage(final Integer messageId) {
        this.messageRepository.deleteById(messageId);
    }

    @Override
    public Page<Message> getAllMessagesByUserId(final Integer userId, final Pageable pageable) {
        this.userService.isUserExistByIdOrThrow(userId);

        return this.messageRepository.findAllBySender_Id(userId, pageable);
    }

    @Override
    public @NotNull Message createMessage(final String content, final Integer senderId, final Integer recipientId) {
        final User sender = this.userService.getUserByIdOrTrow(senderId, "Sender user not found");
        final User recipient = this.userService.getUserByIdOrTrow(recipientId, "Recipient user not found");

        final Message message = Message.builder()
                .messageText(content)
                .messageDate(now())
                .sender(sender)
                .recipient(recipient)
                .build();

        return this.messageRepository.save(message);
    }

    @Override
    public @NotNull Message updateMessage(final Integer id, final String content) {
        final Message message = this.getMessageOrTrow(id);

        message.setMessageText(content);
        return this.messageRepository.save(message);
    }

    private Message getMessageOrTrow(final Integer messageId) {
        return this.messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));
    }
}
