package social.network.backend.socialnetwork.dto.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePostDTO(
        @NotNull(message = "{posts.create.errors.post_text_is_null}")
        @Size(min = 1, max = 1000, message = "{posts.create.errors.post_text_size_is_invalid}")
        String postText,
        @NotNull(message = "{posts.create.errors.user_id_is_null}")
        Integer userId,
        @NotNull(message = "{posts.create.errors.image_is_null}")
        String imageInFormatBase64
) {
}
