package social.network.backend.socialnetwork.dto.message;

public record CreateMessageDTO(
        String content,
        Integer senderId,
        Integer receiverId
) {
}
