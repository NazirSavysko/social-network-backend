package social.network.backend.socialnetwork.facade.mapper.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.message.CreateMessageDTO;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.message.UpdateMessageDTO;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.facade.mapper.Mapper;

@Component("messageMapper")
public final class MessageMapperImpl implements Mapper<Message, UpdateMessageDTO, CreateMessageDTO,GetMessageDTO> {

    private final UserMapperImpl userMapper;

    @Autowired
    public MessageMapperImpl(final UserMapperImpl userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public Message toEntityFromCreate(final @NotNull CreateMessageDTO dtoForCreate) {

        return Message.builder()
                .messageText(dtoForCreate.content())
                .sender(User.builder()
                        .id(dtoForCreate.senderId())
                        .build())
                .recipient(User.builder()
                        .id(dtoForCreate.receiverId())
                        .build())
                .build();
    }

    @Override
    public Message toEntityFromUpdate(final @NotNull UpdateMessageDTO dtoForUpdate) {

        return Message.builder()
                .id(dtoForUpdate.id())
                .sender(User.builder().build())
                .recipient(User.builder().build())
                .messageText(dtoForUpdate.content())
                .build();
    }


    @Contract("_ -> new")
    @Override
    public @NotNull GetMessageDTO toDto(final @NotNull Message entity) {

        return new GetMessageDTO(
                entity.getId(),
                entity.getMessageText(),
                this.userMapper.toDto(entity.getSender()),
                this.userMapper.toDto(entity.getRecipient())
        );
    }
}
