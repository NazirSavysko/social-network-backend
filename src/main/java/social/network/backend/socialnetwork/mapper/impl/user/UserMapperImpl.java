package social.network.backend.socialnetwork.mapper.impl.user;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.mapper.Mapper;

import java.util.List;
import java.util.stream.Stream;

import static social.network.backend.socialnetwork.utils.MapperUtils.mapCollection;


@Component("userMapper")
@AllArgsConstructor
public final class UserMapperImpl implements Mapper<User, GetUserDTO> {

    private final Mapper<Message, GetMessageDTO> messageMapper;
    private final Mapper<Post, GetPostDTO> postMapper;

    @Contract("_ -> new")
    @Override
    public @NotNull GetUserDTO toDto(final @NotNull User entity) {

        return new GetUserDTO(
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getEmail(),
                mapCollection(joinMessages(entity), this.messageMapper::toDto),
                mapCollection(entity.getPosts(), this.postMapper::toDto)
        );
    }

    private @NotNull @Unmodifiable List<Message> joinMessages(final @NotNull User user) {
        return Stream.concat(user.getMessages().stream(), user.getReceivedMessages().stream())
                .toList();
    }
}
