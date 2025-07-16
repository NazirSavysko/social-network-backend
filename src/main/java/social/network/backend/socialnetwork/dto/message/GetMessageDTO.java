package social.network.backend.socialnetwork.dto.message;


import social.network.backend.socialnetwork.dto.user.UserShortDTO;

public record GetMessageDTO(
        Integer id,
        String content,
        UserShortDTO sender,
        UserShortDTO receiver
) {
}
