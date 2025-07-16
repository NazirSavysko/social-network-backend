package social.network.backend.socialnetwork.dto.user;

import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;

import java.util.List;

public record GetUserDTO(
        Integer id,
        String name,
        String surname,
        String email,
        List<GetMessageDTO> messages,
        List<GetPostDTO> posts
) {
}
