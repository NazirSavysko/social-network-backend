package social.network.backend.socialnetwork.mapper.impl.post;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.post_like.GetPostLikeDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;
import social.network.backend.socialnetwork.entity.PostLike;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.mapper.Mapper;

import static social.network.backend.socialnetwork.utils.MapperUtils.mapDto;

@Component
public final class PostLIkeMapperImpl implements Mapper<PostLike, GetPostLikeDTO> {

    private final Mapper<User, UserShortDTO>  userMapper;

    @Autowired
    public PostLIkeMapperImpl(final Mapper<User, UserShortDTO> userMapper) {
        this.userMapper = userMapper;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull GetPostLikeDTO toDto(final @NotNull PostLike entity) {
        return new GetPostLikeDTO(
                entity.getId(),
                mapDto(entity.getUser(),this.userMapper::toDto)
        );
    }
}
