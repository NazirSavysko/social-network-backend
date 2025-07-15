package social.network.backend.socialnetwork.controller.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserPayload(
        String name,
        String surname,
        String email,
        String password
) {
}
