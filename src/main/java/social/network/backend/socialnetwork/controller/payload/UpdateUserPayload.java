package social.network.backend.socialnetwork.controller.payload;

public record UpdateUserPayload(
        String name,
        String surname,
        String email,
        String password
) {
}
