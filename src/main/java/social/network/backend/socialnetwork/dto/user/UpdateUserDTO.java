package social.network.backend.socialnetwork.dto.user;

public record UpdateUserDTO(
        Integer id,
        String name,
        String surname,
        String email,
        String password
) {
}
