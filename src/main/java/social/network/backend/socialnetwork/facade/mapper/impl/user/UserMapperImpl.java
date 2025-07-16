package social.network.backend.socialnetwork.facade.mapper.impl.user;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.facade.mapper.Mapper;

import static social.network.backend.socialnetwork.utils.MapperUtils.mapCollection;


@Component("userMapper")
public final class UserMapperImpl implements Mapper<User, GetUserDTO> {

    private final Mapper<Message, GetMessageDTO> messageMapper;
    private final Mapper<Post, GetPostDTO> postMapper;

    @Autowired
    public UserMapperImpl(final Mapper<Message, GetMessageDTO> messageMapper,
                          final Mapper<Post, GetPostDTO> postMapper) {
        this.messageMapper = messageMapper;
        this.postMapper = postMapper;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull GetUserDTO toDto(final @NotNull User entity) {

        return new GetUserDTO(
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getEmail(),
                mapCollection(entity.getMessages(), this.messageMapper::toDto),
                mapCollection(entity.getPosts(), this.postMapper::toDto)
        );
    }
}
