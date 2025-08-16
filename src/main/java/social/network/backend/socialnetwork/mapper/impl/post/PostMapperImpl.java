package social.network.backend.socialnetwork.mapper.impl.post;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.post_comment.GetPostCommentDTO;
import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.PostComment;
import social.network.backend.socialnetwork.mapper.Mapper;

import static social.network.backend.socialnetwork.utils.FileUtils.getContentFromFile;
import static social.network.backend.socialnetwork.utils.MapperUtils.mapCollection;
@Component
@AllArgsConstructor
public final class PostMapperImpl implements Mapper<Post, GetPostDTO> {

    private final Mapper<PostComment, GetPostCommentDTO> postCommentMapper;

    @Contract("_ -> new")
    @Override
    public @NotNull GetPostDTO toDto(final @NotNull Post entity) {
        return new GetPostDTO(
                entity.getPostLikes().size(),
                mapCollection(entity.getPostComments(), this.postCommentMapper::toDto),
                entity.getPostText(),
                entity.getPostDate(),
                entity.getId(),
                getContentFromFile(entity.getImage().getFilePath())
        );
    }
}
