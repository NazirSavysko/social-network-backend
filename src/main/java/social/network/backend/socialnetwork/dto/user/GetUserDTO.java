package social.network.backend.socialnetwork.dto.user;

public record GetUserDTO(
        Integer id,
        String name,
        String surname,
        String email
) {
}
