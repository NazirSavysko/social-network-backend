package social.network.backend.socialnetwork.mapper.impl.message;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.mapper.Mapper;

import static social.network.backend.socialnetwork.utils.MapperUtils.mapDto;

@Component("messageMapper")
public final class MessageMapperImpl implements Mapper<Message,GetMessageDTO> {

    private final Mapper<User, UserShortDTO> userMapper;

    @Autowired
    public MessageMapperImpl(final Mapper<User, UserShortDTO>  userMapper) {
        this.userMapper = userMapper;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull GetMessageDTO toDto(final @NotNull Message entity) {

        return new GetMessageDTO(
                entity.getId(),
                entity.getMessageText(),
                mapDto(entity.getSender(),this.userMapper::toDto),
                mapDto(entity.getRecipient(),this.userMapper::toDto)
        );
    }
}
