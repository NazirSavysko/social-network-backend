package social.network.backend.socialnetwork.dto.message;

import social.network.backend.socialnetwork.dto.user.GetUserDTO;

public record GetMessageDTO(
        Integer id,
        String content,
        GetUserDTO sender,
        GetUserDTO receiver
) {
}
