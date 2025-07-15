package social.network.backend.socialnetwork.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMessageDTO(
        Integer id,
        @NotBlank(message = "{messages.update.errors.content_is_blank}")
        @Size(max = 1000, message = "{messages.update.errors.content_size_is_invalid}")
        String content
) {
}
