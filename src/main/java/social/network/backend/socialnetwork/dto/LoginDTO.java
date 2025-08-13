package social.network.backend.socialnetwork.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "{users.create.errors.email_is_blank}")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "{users.create.errors.email_is_invalid}")
        @Size(max = 30, message = "{users.create.errors.email_size_is_invalid}")
        String email,
        @NotBlank(message = "{users.create.errors.password_is_blank}")
        @Size(min = 8, max = 30, message = "{users.create.errors.password_size_is_invalid}")
        String password
) {
}
