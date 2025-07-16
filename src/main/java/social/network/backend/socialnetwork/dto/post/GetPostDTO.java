package social.network.backend.socialnetwork.dto.post;

import social.network.backend.socialnetwork.dto.image.GetImageDTO;

import java.time.LocalDateTime;
import java.util.List;

public record GetPostDTO(
        List<GetPostLikeDTO> likes,
        List<GetPostCommentDTO> comments,
        String postText,
        LocalDateTime postDate,
        Integer id,
        GetImageDTO image
) {
}
