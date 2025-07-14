package social.network.backend.socialnetwork.dto.user;

public record CreateUserDTO(
        String name,
        String surname,
        String email,
        String password
) {
}
