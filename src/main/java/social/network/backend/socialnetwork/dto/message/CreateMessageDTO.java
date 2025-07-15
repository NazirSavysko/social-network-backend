package social.network.backend.socialnetwork.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateMessageDTO(
        @NotBlank(message = "{messages.create.errors.content_is_blank}")
        @Size(max = 1000, message = "{messages.create.errors.content_size_is_invalid}")
        String content,
        @NotNull(message = "{messages.create.errors.sender_id_is_null}")
        Integer senderId,
        @NotNull(message = "{messages.create.errors.receiver_id_is_null}")
        Integer receiverId
) {
}
