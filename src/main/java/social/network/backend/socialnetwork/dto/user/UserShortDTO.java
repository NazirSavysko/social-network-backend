package social.network.backend.socialnetwork.dto.user;


public record UserShortDTO(
        Integer id,
        String name,
        String surname,
        String email
) {
}
