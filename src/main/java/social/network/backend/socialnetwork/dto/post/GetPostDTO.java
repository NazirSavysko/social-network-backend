package social.network.backend.socialnetwork.dto.post;

import social.network.backend.socialnetwork.dto.post_comment.GetPostCommentDTO;

import java.time.LocalDateTime;
import java.util.List;

public record GetPostDTO(
        Integer likes,
        List<GetPostCommentDTO> comments,
        String postText,
        LocalDateTime postDate,
        Integer id,
        String image
) {
}
