package social.network.backend.socialnetwork.dto.post;

import social.network.backend.socialnetwork.dto.user.UserShortDTO;

public record GetPostLikeDTO(
        Integer id,
        UserShortDTO user
) {
}
