package social.network.backend.socialnetwork.facade.mapper.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.facade.mapper.Mapper;

@Component("messageMapper")
public final class MessageMapperImpl implements Mapper<Message,GetMessageDTO> {

    private final UserMapperImpl userMapper;

    @Autowired
    public MessageMapperImpl(final UserMapperImpl userMapper) {
        this.userMapper = userMapper;
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
