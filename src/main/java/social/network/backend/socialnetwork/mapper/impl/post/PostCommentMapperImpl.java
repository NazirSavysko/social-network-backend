package social.network.backend.socialnetwork.mapper.impl.post;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.post_comment.GetPostCommentDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;
import social.network.backend.socialnetwork.entity.PostComment;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.mapper.Mapper;

import static social.network.backend.socialnetwork.utils.MapperUtils.mapDto;

@Component
@AllArgsConstructor
public final class PostCommentMapperImpl implements Mapper<PostComment, GetPostCommentDTO> {

    private final Mapper<User, UserShortDTO> userMapper;

    @Contract("_ -> new")
    @Override
    public @NotNull GetPostCommentDTO toDto(final @NotNull PostComment entity) {
        return new GetPostCommentDTO(
                entity.getId(),
                entity.getCommentText(),
                entity.getCommentText(),
                mapDto(entity.getUser(), this.userMapper::toDto)
        );
    }
}
