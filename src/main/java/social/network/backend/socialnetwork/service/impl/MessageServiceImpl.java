package social.network.backend.socialnetwork.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public Message createMessage(final @NotNull Message message) {
        return Message.builder()
                .id(1)
                .messageText(message.getMessageText())
                .sender(message.getSender())
                .recipient(message.getRecipient())
                .build();
    }

    @Override
    public Message getMessageById(final Integer messageId) {

        return Message.builder()
                .id(messageId)
                .sender(User.builder().build())
                .recipient(User.builder().build())
                .build();
    }

    @Override
    public Message updateMessage(final @NotNull Message message) {
        return Message.builder()
                .id(message.getId())
                .messageText(message.getMessageText())
                .sender(User.builder().build())
                .recipient(User.builder().build())
                .build();
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
}
