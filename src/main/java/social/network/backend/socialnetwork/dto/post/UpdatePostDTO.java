package social.network.backend.socialnetwork.dto.post;

public record UpdatePostDTO(
        String text,
        String imageInFormatBase64,
        Integer id
) {
}
