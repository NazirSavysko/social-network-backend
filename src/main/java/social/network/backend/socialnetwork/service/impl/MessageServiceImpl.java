package social.network.backend.socialnetwork.service.impl;

import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.service.MessageService;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {


    @Override
    public Optional<Message> getMessageById(final Integer messageId) {

        return Optional.of(
                Message.builder()
                        .id(messageId)
                        .sender(User.builder().build())
                        .recipient(User.builder().build())
                        .build()
        );
    }

    @Override
    public void deleteMessage(final Integer messageId) {

    }

    @Override
    public List<Message> getAllMessagesByUserId(final Integer userId) {

        return List.of(
                Message.builder()
                        .sender(User.builder().build())
                        .recipient(User.builder().build())
                        .build(),
                Message.builder()
                        .sender(User.builder().build())
                        .recipient(User.builder().build())
                        .build(),
                Message.builder()
                        .sender(User.builder().build())
                        .recipient(User.builder().build())
                        .build(),
                Message.builder()
                        .sender(User.builder().build())
                        .recipient(User.builder().build())
                        .build()
        );
    }

    @Override
    public Message createMessage(final String content, final Integer senderId, final Integer recipientId) {
        final User sender = User.builder().id(senderId).build();
        final User recipient = User.builder().id(recipientId).build();

        return Message.builder()
                .id(1)
                .sender(sender)
                .recipient(recipient)
                .messageText(content)
                .build();
    }

    @Override
    public Message updateMessage(final Integer id, final String content) {
        final User sender = User.builder().build();
        final User recipient = User.builder().build();

        return Message.builder()
                .id(id)
                .sender(sender)
                .recipient(recipient)
                .messageText(content)
                .build();
    }
}
