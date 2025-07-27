package social.network.backend.socialnetwork.dto.post;

import social.network.backend.socialnetwork.dto.post_comment.GetPostCommentDTO;
import social.network.backend.socialnetwork.dto.post_like.GetPostLikeDTO;

import java.time.LocalDateTime;
import java.util.List;

public record GetPostDTO(
        List<GetPostLikeDTO> likes,
        List<GetPostCommentDTO> comments,
        String postText,
        LocalDateTime postDate,
        Integer id,
        String image
) {
}
