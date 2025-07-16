package social.network.backend.socialnetwork.dto.post;

import social.network.backend.socialnetwork.dto.user.UserShortDTO;

public record GetPostCommentDTO(
        Integer id,
        String commentText,
        String text,
        UserShortDTO user) {
}
