package social.network.backend.socialnetwork.controller.payload;

public record UpdatePostPayload(
        String text,
        String imageInFormatBase64
) {
}
